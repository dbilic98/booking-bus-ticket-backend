package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestRouteDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseRouteDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseScheduleDto;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import com.bookingbustickets.bookingbustickets.domain.service.RouteService;
import com.bookingbustickets.bookingbustickets.exception.ScheduleDateException;
import com.bookingbustickets.bookingbustickets.util.RoleBasedAccessHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
    public PaginatedResponse<ResponseRouteDto> getRoutes (
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Page<Route> allRoutes;

        if (RoleBasedAccessHelper.isAdmin(authentication)) {
            allRoutes = routeService.getAllRoutes(pageNumber, pageSize);
        } else if (RoleBasedAccessHelper.isCompany(authentication)) {
            String companyId = authentication.getName();
            allRoutes = routeService.getAllRoutesByCompany(UUID.fromString(companyId), pageNumber, pageSize);
        } else {
            throw new AccessDeniedException("Access is denied");
        }

        Page<ResponseRouteDto> map = allRoutes.map(this::toResponseDto);
        return new PaginatedResponse<>(map);
    }
    private ResponseRouteDto toResponseDto(Route route) {
        return new ResponseRouteDto(
                route.getId(),
                route.getBasePrice(),
                route.getTotalDistance(),
                route.getStartPlace().getId(),
                route.getEndPlace().getId());
    }
    @GetMapping("/{id}")
    public ResponseRouteDto findRouteById(@PathVariable Long id) {
        Route route = routeService.findRouteById(id);
        return new ResponseRouteDto(
                route.getId(),
                route.getBasePrice(),
                route.getTotalDistance(),
                route.getStartPlace().getId(),
                route.getEndPlace().getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
    public ResponseRouteDto createRoute(@Valid @RequestBody RequestRouteDto requestRouteDto) {
        Route createdRoute = routeService.createRoute(requestRouteDto);
        return new ResponseRouteDto(
                createdRoute.getId(),
                createdRoute.getBasePrice(),
                createdRoute.getTotalDistance(),
                createdRoute.getStartPlace().getId(),
                createdRoute.getEndPlace().getId());
    }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public ResponseRouteDto updateRoute(
      @Valid @PathVariable("id") Long id, @RequestBody RequestRouteDto requestRouteDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Route updatedRoute;
    if (RoleBasedAccessHelper.isAdmin(authentication)) {
      updatedRoute = routeService.updateRoute(id, requestRouteDto);
    } else if (RoleBasedAccessHelper.isCompany(authentication)) {
      String companyUuid = authentication.getName();
      Route route = routeService.findRouteById(id);
      RoleBasedAccessHelper.checkCompanyAccess(
          authentication,
          UUID.fromString(companyUuid),
          route.getScheduleList().get(0).getBus().getCompany().getCompanyUuid());
      updatedRoute = routeService.updateRoute(id, requestRouteDto);
    } else {
      throw new AccessDeniedException("Access denied");
    }
    return new ResponseRouteDto(
        updatedRoute.getId(),
        updatedRoute.getBasePrice(),
        updatedRoute.getTotalDistance(),
        updatedRoute.getStartPlace().getId(),
        updatedRoute.getEndPlace().getId());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public void deleteRoute(@PathVariable("id") Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (RoleBasedAccessHelper.isCompany(authentication)) {
      String companyUuid = authentication.getName();
      Route route = routeService.findRouteById(id);
      RoleBasedAccessHelper.checkCompanyAccess(
          authentication,
          UUID.fromString(companyUuid),
          route.getScheduleList().get(0).getBus().getCompany().getCompanyUuid());
    }
    routeService.deleteRoute(id);
  }

    @GetMapping
    public List<ResponseRouteDto> findRoutes(
            @RequestParam("startPlaceId") Long startPlaceId,
            @RequestParam("endPlaceId") Long endPlaceId,
            @RequestParam("scheduleDate") LocalDate startScheduleDate,
            @RequestParam(value = "endScheduleDate", required = false) LocalDate endScheduleDate) {

        if (endScheduleDate != null && endScheduleDate.isBefore(startScheduleDate)) {
            throw new ScheduleDateException("End schedule date cannot be before start schedule date.");
        }

        List<Route> oneWayRoutes = routeService.findFilteredRoutes(startPlaceId, endPlaceId, startScheduleDate);

        if (endScheduleDate == null) {
            return mapRoutesToResponseRouteDtos(oneWayRoutes, startScheduleDate);
        }

        List<Route> returnRoutes = routeService.findFilteredRoutes(endPlaceId, startPlaceId, endScheduleDate);

        List<Route> mergedRoutes = new ArrayList<>(oneWayRoutes.size() + returnRoutes.size());
        mergedRoutes.addAll(oneWayRoutes);
        mergedRoutes.addAll(returnRoutes);

        return mapRoutesToResponseRouteDtos(mergedRoutes, startScheduleDate, endScheduleDate);
    }

    private List<ResponseRouteDto> mapRoutesToResponseRouteDtos(List<Route> routes, LocalDate scheduleDate) {
        List<ResponseRouteDto> responseRouteDtos = new ArrayList<>();
        for (Route route : routes) {
            List<ResponseScheduleDto> scheduleListDto = mapToResponseScheduleDto(route.getScheduleList(), scheduleDate);
            if (!scheduleListDto.isEmpty()) {
                ResponseRouteDto routeDto = mapToResponseRouteDto(route, scheduleListDto);
                responseRouteDtos.add(routeDto);
            }
        }
        return responseRouteDtos;
    }

    private List<ResponseRouteDto> mapRoutesToResponseRouteDtos(List<Route> routes, LocalDate startScheduleDate, LocalDate endScheduleDate) {
        List<ResponseRouteDto> responseRouteDtos = new ArrayList<>();
        for (Route route : routes) {
            List<ResponseScheduleDto> scheduleListDto = mapToResponseScheduleDto(route.getScheduleList(), startScheduleDate, endScheduleDate);
            if (!scheduleListDto.isEmpty()) {
                ResponseRouteDto routeDto = mapToResponseRouteDto(route, scheduleListDto);
                responseRouteDtos.add(routeDto);
            }
        }
        return responseRouteDtos;
    }

    private ResponseRouteDto mapToResponseRouteDto(Route route, List<ResponseScheduleDto> scheduleListDto) {
        return new ResponseRouteDto(
                route.getId(),
                route.getBasePrice(),
                route.getTotalDistance(),
                route.getStartPlace().getId(),
                route.getEndPlace().getId(),
                scheduleListDto);
    }

    private List<ResponseScheduleDto> mapToResponseScheduleDto(List<Schedule> scheduleList, LocalDate scheduleDate) {
        List<ResponseScheduleDto> responseScheduleDtos = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            if (schedule.getScheduleDate().isEqual(scheduleDate)) {
                ResponseScheduleDto scheduleDto = new ResponseScheduleDto(
                        schedule.getId(),
                        schedule.getScheduleDate(),
                        schedule.getDepartureTime(),
                        schedule.getArrivalTime(),
                        schedule.getBus().getCompany().getCompanyName());
                responseScheduleDtos.add(scheduleDto);
            }
        }
        return responseScheduleDtos;
    }

    private List<ResponseScheduleDto> mapToResponseScheduleDto(List<Schedule> scheduleList, LocalDate startScheduleDate, LocalDate endScheduleDate) {
        List<ResponseScheduleDto> responseScheduleDtos = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            if (schedule.getScheduleDate().isEqual(startScheduleDate) || schedule.getScheduleDate().isEqual(endScheduleDate)) {
                ResponseScheduleDto scheduleDto = new ResponseScheduleDto(
                        schedule.getId(),
                        schedule.getScheduleDate(),
                        schedule.getDepartureTime(),
                        schedule.getArrivalTime(),
                        schedule.getBus().getCompany().getCompanyName());
                responseScheduleDtos.add(scheduleDto);
            }
        }
        return responseScheduleDtos;
    }

}
