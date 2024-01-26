package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestTicketDto;
import com.bookingbustickets.bookingbustickets.domain.model.PassengerCategory;
import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.bookingbustickets.bookingbustickets.domain.model.Ticket;
import com.bookingbustickets.bookingbustickets.domain.repository.PassengerCategoryRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.ReservationRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.RouteRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final ReservationRepository reservationRepository;

    private final PassengerCategoryRepository passengerCategoryRepository;

    private final RouteRepository routeRepository;

    public TicketService(TicketRepository ticketRepository, ReservationRepository reservationRepository, PassengerCategoryRepository passengerCategoryRepository, RouteRepository routeRepository) {
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
        this.passengerCategoryRepository = passengerCategoryRepository;
        this.routeRepository = routeRepository;
    }

    public Ticket findTicketById(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isEmpty()) {
            throw new RuntimeException("Ticket with ID " + id + "does not found");
        }
        return optionalTicket.get();
    }

    public Page<Ticket> getAllTicket(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ticketRepository.findAll(pageable);
    }

    public Ticket createTicket(RequestTicketDto requestTicketDto) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(requestTicketDto.getReservationId());
        if (optionalReservation.isEmpty()) {
            throw new RuntimeException("Reservation with the given ID is not found");
        }

        Optional<PassengerCategory> optionalPassengerCategory = passengerCategoryRepository.findById(requestTicketDto.getPassengerCategoryId());
        if (optionalPassengerCategory.isEmpty()) {
            throw new RuntimeException("Passenger category with the given ID is not found");
        }

        Optional<Route> optionalOneWayRoute = routeRepository.findById(requestTicketDto.getOneWayRouteId());
        if (optionalOneWayRoute.isEmpty()) {
            throw new RuntimeException("One-way route with the given ID is not found");
        }

        Optional<Route> optionalReturnRoute;
        if (requestTicketDto.getReturnRouteId() != null) {
            optionalReturnRoute = routeRepository.findById(requestTicketDto.getReturnRouteId());
            if (optionalReturnRoute.isEmpty()) {
                throw new RuntimeException("Return route with the given ID is not found");
            }
        } else {
            optionalReturnRoute = Optional.empty();
        }

        Ticket ticket = new Ticket();
        ticket.calculatePrice(optionalOneWayRoute.get(), optionalReturnRoute.orElse(null), optionalPassengerCategory.get());

        ticket.setReservation(optionalReservation.get());
        ticket.setOneWayRoute(optionalOneWayRoute.get());
        ticket.setReturnRoute(optionalReturnRoute.orElse(null));
        ticket.setPassengerCategory(optionalPassengerCategory.get());

        if (requestTicketDto.getDateOfDeparture() != null) {
            ticket.setDateOfDeparture(requestTicketDto.getDateOfDeparture());
        } else {
            throw new RuntimeException("Date of departure is required.");
        }

        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id, RequestTicketDto requestTicketDto) {
        Ticket ticketToUpdate = findTicketById(id);
        ticketToUpdate.setDateOfDeparture(requestTicketDto.getDateOfDeparture());
        return ticketRepository.save(ticketToUpdate);
    }

    public void deleteTicket(@PathVariable("id") Long id) {
        try {
            ticketRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Ticket with ID " + id + "does not exist");
        }
    }
}
