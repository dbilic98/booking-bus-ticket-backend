package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.domain.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/reserve/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void reserveSeat(@Valid @PathVariable("id") Long id) {
        seatService.reserveSeat(id);
    }
}
