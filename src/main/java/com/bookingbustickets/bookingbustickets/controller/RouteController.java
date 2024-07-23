package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestRouteDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseRouteDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseScheduleDto;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import com.bookingbustickets.bookingbustickets.domain.service.RouteService;
import com.bookingbustickets.bookingbustickets.exception.ScheduleDateException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
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
    public ResponseRouteDto updateRoute(@Valid @PathVariable("id") Long id, @RequestBody RequestRouteDto requestRouteDto) {
        Route updatedRoute = routeService.updateRoute(id, requestRouteDto);
        return new ResponseRouteDto(
                updatedRoute.getId(),
                updatedRoute.getBasePrice(),
                updatedRoute.getTotalDistance(),
                updatedRoute.getStartPlace().getId(),
                updatedRoute.getEndPlace().getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable("id") Long id) {
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
