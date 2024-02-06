package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestRouteDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseRouteDto;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.bookingbustickets.bookingbustickets.domain.service.RouteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return new ResponseRouteDto(route.getId(), route.getBasePrice(), route.getTotalDistance(), route.getStartPlace().getId(), route.getEndPlace().getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseRouteDto createRoute(@RequestBody RequestRouteDto requestRouteDto) {
        Route createdRoute = routeService.createRoute(requestRouteDto);
        return new ResponseRouteDto(createdRoute.getId(), createdRoute.getBasePrice(), createdRoute.getTotalDistance(), createdRoute.getStartPlace().getId(), createdRoute.getEndPlace().getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRouteDto updateRoute(@PathVariable("id") Long id, @RequestBody RequestRouteDto requestRouteDto) {
        Route updatedRoute = routeService.updateRoute(id, requestRouteDto);
        return new ResponseRouteDto(updatedRoute.getId(), updatedRoute.getBasePrice(), updatedRoute.getTotalDistance(), updatedRoute.getStartPlace().getId(), updatedRoute.getEndPlace().getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable("id") Long id) {
        routeService.deleteRoute(id);
    }

    @GetMapping
    public Page<Route> getAllRoute(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        return routeService.getAllRoute(pageNumber, pageSize);
    }

    @GetMapping("/find-start-to-end-places-and-schedule-date")
    public List<Route> findRoutes(
            @RequestParam Long startPlaceId,
            @RequestParam Long endPlaceId,
            @RequestParam LocalDate scheduleDate) {

        return routeService.findRoutesByStartAndEndPlaceAndScheduleDate(startPlaceId, endPlaceId, scheduleDate);
    }
}
