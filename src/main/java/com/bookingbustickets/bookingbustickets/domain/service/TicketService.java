package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestTicketDto;
import com.bookingbustickets.bookingbustickets.domain.model.*;
import com.bookingbustickets.bookingbustickets.domain.repository.*;
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

    private final ScheduleRepository scheduleRepository;

    public TicketService(TicketRepository ticketRepository, ReservationRepository reservationRepository, PassengerCategoryRepository passengerCategoryRepository, RouteRepository routeRepository, ScheduleRepository scheduleRepository) {
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
        this.passengerCategoryRepository = passengerCategoryRepository;
        this.routeRepository = routeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Ticket findTicketById(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isEmpty()) {
            throw new RuntimeException("Ticket with ID " + id + "does not exist");
        }
        return optionalTicket.get();
    }

    public Page<Ticket> getAllTicket(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ticketRepository.findAll(pageable);
    }

    public void calculatePrice(Ticket ticket, Route oneWayRoute, Route returnRoute, PassengerCategory passengerCategory) {
        float basePrice;
        if (returnRoute != null) {
            basePrice = returnRoute.getBasePrice() * 2;
        } else {
            basePrice = oneWayRoute.getBasePrice();
        }
        float discountPercentage = passengerCategory.getDiscountPercentage();

        float calculatedPrice = basePrice * discountPercentage;
        ticket.setPrice(calculatedPrice);
    }

    public Ticket createTicket(RequestTicketDto requestTicketDto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(requestTicketDto.getScheduleDateId());
        if (optionalSchedule.isEmpty()) {
            throw new RuntimeException("Schedule with the given ID is not found");
        }

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
        calculatePrice(ticket, optionalOneWayRoute.get(), optionalReturnRoute.orElse(null), optionalPassengerCategory.get());

        ticket.setSchedule(optionalSchedule.get());
        ticket.setReservation(optionalReservation.get());
        ticket.setOneWayRoute(optionalOneWayRoute.get());
        ticket.setPassengerCategory(optionalPassengerCategory.get());
        ticket.setReturnRoute(optionalReturnRoute.orElse(null));

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(@PathVariable("id") Long id) {
        try {
            ticketRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Ticket with ID " + id + "does not exist");
        }
    }
}

