package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.response.ResponseReservationDto;
import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import com.bookingbustickets.bookingbustickets.domain.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseReservationDto findReservationById(@PathVariable Long id){
        Reservation reservation = reservationService.findReservationById(id);
        return new ResponseReservationDto(reservation.getId(), reservation.getDateOfReservation(), reservation.getStatus(), reservation.getTickets());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseReservationDto createReservation(){
        Reservation createdReservation = reservationService.createReservation();
        return new ResponseReservationDto(createdReservation.getId(), createdReservation.getDateOfReservation(), createdReservation.getStatus(), createdReservation.getTickets());
    }

    @PutMapping("/confirm/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmReservation(@PathVariable("id") Long id){
        reservationService.confirmReservation(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable("id") Long id){
        reservationService.deleteReservation(id);
    }
}


