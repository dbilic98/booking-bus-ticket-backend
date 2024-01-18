package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestReservationDto;
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
        return new ResponseReservationDto(reservation.getId(), reservation.getDateOfReservation(), reservation.getStatus(),reservation.getPassengerCategory().getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseReservationDto createReservation(@RequestBody RequestReservationDto requestReservationDto){
        Reservation createdReservation = reservationService.createReservation(requestReservationDto);
        return new ResponseReservationDto(createdReservation.getId(), createdReservation.getDateOfReservation(), createdReservation.getStatus(), createdReservation.getPassengerCategory().getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseReservationDto updateReservation(@PathVariable("id") Long id, @RequestBody RequestReservationDto requestReservationDto){
        Reservation updatedReservation = reservationService.updateReservation(id, requestReservationDto);
        return new ResponseReservationDto(updatedReservation.getId(),updatedReservation.getDateOfReservation(), updatedReservation.getStatus(), updatedReservation.getPassengerCategory().getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable("id") Long id){
        reservationService.deleteReservation(id);
    }
}
