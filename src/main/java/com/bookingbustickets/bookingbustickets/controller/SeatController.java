package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.response.ResponseSeatDto;
import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import com.bookingbustickets.bookingbustickets.domain.service.SeatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<ResponseSeatDto> findSeats(
            @RequestParam("routeId") Long routeId,
            @RequestParam("scheduleId") Long scheduleId) {

        List<Seat> seats = seatService.findSeatsByRouteAndSchedule(routeId, scheduleId);
        return mapToResponseSeatDto(seats);
    }

    private List<ResponseSeatDto> mapToResponseSeatDto(List<Seat> seats) {
        List<ResponseSeatDto> responseSeatDtoList = new ArrayList<>();

        for (Seat seat : seats) {
            ResponseSeatDto responseSeatDto = new ResponseSeatDto(
                    seat.getId(),
                    seat.getSeatNumber());
            responseSeatDtoList.add(responseSeatDto);
        }
        return responseSeatDtoList;
    }
}