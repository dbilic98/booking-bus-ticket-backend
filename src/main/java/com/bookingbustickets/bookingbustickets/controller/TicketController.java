package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestTicketDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseTicketDto;
import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import com.bookingbustickets.bookingbustickets.domain.model.Ticket;
import com.bookingbustickets.bookingbustickets.domain.service.ReservationService;
import com.bookingbustickets.bookingbustickets.domain.service.ScheduleService;
import com.bookingbustickets.bookingbustickets.domain.service.TicketService;
import com.bookingbustickets.bookingbustickets.util.RoleBasedAccessHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ReservationService reservationService;
    private final ScheduleService scheduleService;

    public TicketController(TicketService ticketService, ReservationService reservationService, ScheduleService scheduleService) {
        this.ticketService = ticketService;
        this.reservationService = reservationService;
        this.scheduleService = scheduleService;
    }

  @GetMapping
  public PaginatedResponse<ResponseTicketDto> getTickets(
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "10") int pageSize) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Page<Ticket> allTickets;
    if (RoleBasedAccessHelper.isAdmin(authentication)) {
      allTickets = ticketService.getAllTickets(pageNumber, pageSize);
    } else if (RoleBasedAccessHelper.isCompany(authentication)) {
      UUID companyUuid = UUID.fromString(authentication.getName());
      allTickets = ticketService.getAllTicketsByCompanyUuid(pageNumber, pageSize, companyUuid);
    } else if (RoleBasedAccessHelper.isClient(authentication)) {
      UUID userUuid = UUID.fromString(authentication.getName());
      allTickets = ticketService.getAllTicketsByUserUuid(pageNumber, pageSize, userUuid);
    } else {
      throw new AccessDeniedException("Access denied");
    }

    Page<ResponseTicketDto> map = allTickets.map(this::toResponseDto);
    return new PaginatedResponse<>(map);
  }

    private ResponseTicketDto toResponseDto(Ticket ticket) {
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

    @GetMapping("/{id}")
    public ResponseTicketDto findTicketById(@PathVariable("id") Long id) {
        Ticket ticket = ticketService.findTicketById(id);
       return toResponseDto(ticket);
    }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseTicketDto createTicket(@Valid @RequestBody RequestTicketDto requestTicketDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (RoleBasedAccessHelper.isClient(authentication)) {
      UUID userUuid = UUID.fromString(authentication.getName());
      Reservation reservation =
          reservationService.findReservationById(requestTicketDto.getReservationId());
      RoleBasedAccessHelper.checkClientAccess(
          authentication, userUuid, reservation.getUser().getUserUuid());
    }
    if (RoleBasedAccessHelper.isCompany(authentication)) {
      UUID companyUuid = UUID.fromString(authentication.getName());
      Schedule scheduleById =
          scheduleService.findScheduleById(requestTicketDto.getOneWayScheduleId());
      RoleBasedAccessHelper.checkCompanyAccess(
          authentication, companyUuid, scheduleById.getBus().getCompany().getCompanyUuid());
    }
    Ticket createdTicket = ticketService.createTicket(requestTicketDto);
    return toResponseDto(createdTicket);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public void deleteTicket(@PathVariable("id") Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (RoleBasedAccessHelper.isCompany(authentication)) {
      UUID companyUuid = UUID.fromString(authentication.getName());
      Ticket ticketById = ticketService.findTicketById(id);
      RoleBasedAccessHelper.checkCompanyAccess(
          authentication,
          companyUuid,
          ticketById
              .getOneWayRoute()
              .getScheduleList()
              .get(0)
              .getBus()
              .getCompany()
              .getCompanyUuid());
    }
  }

    @GetMapping("/sumTotalPrice")
    public float getTotalPrice(@RequestParam("reservationId") Long reservationId) {
        return ticketService.getTotalPrice(reservationId);
    }
}
