package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestTicketDto;
import com.bookingbustickets.bookingbustickets.domain.model.*;
import com.bookingbustickets.bookingbustickets.domain.repository.*;
import com.bookingbustickets.bookingbustickets.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            throw new TicketNotFoundException("Ticket with ID " + id + " is not found");
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
            throw new ScheduleNotFoundException("Schedule with the given ID is not found");
        }

        Optional<Reservation> optionalReservation = reservationRepository.findById(requestTicketDto.getReservationId());
        if (optionalReservation.isEmpty()) {
            throw new ReservationNotFoundException("Reservation with the given ID is not found");
        }

        Optional<PassengerCategory> optionalPassengerCategory = passengerCategoryRepository.findById(requestTicketDto.getPassengerCategoryId());
        if (optionalPassengerCategory.isEmpty()) {
            throw new PassengerCategoryNotFoundException("Passenger category with the given ID is not found");
        }

        Optional<Route> optionalOneWayRoute = routeRepository.findById(requestTicketDto.getOneWayRouteId());
        if (optionalOneWayRoute.isEmpty()) {
            throw new RouteNotFoundException("One-way route with the given ID is not found");
        }

        Optional<Route> optionalReturnRoute;
        if (requestTicketDto.getReturnRouteId() != null) {
            optionalReturnRoute = routeRepository.findById(requestTicketDto.getReturnRouteId());
            if (optionalReturnRoute.isEmpty()) {
                throw new RouteNotFoundException("Return route with the given ID is not found");
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

    public void deleteTicket(Long id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
        } else {
            throw new TicketNotFoundException("Ticket with ID " + id + " is not found");
        }
    }
}

