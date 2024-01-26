package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestRouteDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseRouteDto;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.bookingbustickets.bookingbustickets.domain.service.RouteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/{id}")
    public ResponseRouteDto findRouteById(@PathVariable Long id) {
        Route route = routeService.findRouteById(id);
        return new ResponseRouteDto(route.getId(), route.getStartPoint(), route.getEndPoint(), route.getBasePrice(), route.getTotalDistance());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseRouteDto createRoute(@RequestBody RequestRouteDto requestRouteDto) {
        Route createdRoute = routeService.createRoute(requestRouteDto);
        return new ResponseRouteDto(createdRoute.getId(), createdRoute.getStartPoint(), createdRoute.getEndPoint(), createdRoute.getBasePrice(), createdRoute.getTotalDistance());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRouteDto updateRoute(@PathVariable("id") Long id, @RequestBody RequestRouteDto requestRouteDto) {
        Route updatedRoute = routeService.updateRoute(id, requestRouteDto);
        return new ResponseRouteDto(updatedRoute.getId(), updatedRoute.getStartPoint(), updatedRoute.getEndPoint(), updatedRoute.getBasePrice(), updatedRoute.getTotalDistance());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable("id") Long id) {
        routeService.deleteRoute(id);
    }

    @GetMapping
    public Page<Route> getAllRoute(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return routeService.getAllRoute(pageNumber, pageSize);
    }
}
