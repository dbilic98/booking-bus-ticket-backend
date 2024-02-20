package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestTicketDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseTicketDto;
import com.bookingbustickets.bookingbustickets.domain.model.Ticket;
import com.bookingbustickets.bookingbustickets.domain.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public ResponseTicketDto findTicketById(@PathVariable("id") Long id) {
        Ticket ticket = ticketService.findTicketById(id);
        return new ResponseTicketDto(
                ticket.getId(),
                ticket.getPrice(),
                ticket.getOneWaySchedule().getId(),
                ticket.getReturnSchedule() == null ? null : ticket.getReturnSchedule().getId(),
                ticket.getReservation().getId(),
                ticket.getOneWayRoute().getId(),
                ticket.getReturnRoute() == null ? null : ticket.getReturnRoute().getId(),
                ticket.getPassengerCategory().getId(),
                ticket.getOneWaySeat().getId(),
                ticket.getReturnSeat() == null ? null : ticket.getReturnSeat().getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTicketDto createTicket(@Valid @RequestBody RequestTicketDto requestTicketDto) {
        Ticket createdTicket = ticketService.createTicket(requestTicketDto);
        return new ResponseTicketDto(
                createdTicket.getId(),
                createdTicket.getPrice(),
                createdTicket.getOneWaySchedule().getId(),
                createdTicket.getReturnSchedule() == null ? null : createdTicket.getReturnSchedule().getId(),
                createdTicket.getReservation().getId(),
                createdTicket.getOneWayRoute().getId(),
                createdTicket.getReturnRoute() == null ? null : createdTicket.getReturnRoute().getId(),
                createdTicket.getPassengerCategory().getId(),
                createdTicket.getOneWaySeat().getId(),
                createdTicket.getReturnSeat() == null ? null : createdTicket.getReturnSeat().getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicket(id);
    }

    @GetMapping
    public Page<Ticket> getTicket(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ticketService.getAllTicket(pageNumber, pageSize);
    }
}
