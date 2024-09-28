package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseReservationDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseTicketDto;
import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import com.bookingbustickets.bookingbustickets.domain.model.Ticket;
import com.bookingbustickets.bookingbustickets.domain.model.User;
import com.bookingbustickets.bookingbustickets.domain.service.ReservationService;
import com.bookingbustickets.bookingbustickets.domain.service.UserService;
import com.bookingbustickets.bookingbustickets.util.RoleBasedAccessHelper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping
    public PaginatedResponse<ResponseReservationDto> getReservations(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<Reservation> allReservations;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (RoleBasedAccessHelper.isAdmin(authentication)) {
            allReservations = reservationService.getAllReservations(pageNumber, pageSize);
        } else if (RoleBasedAccessHelper.isCompany(authentication)) {
            String companyId = authentication.getName();
            allReservations =
                    reservationService.getAllReservationsByCompanyUuid(
                            pageNumber, pageSize, UUID.fromString(companyId));
        } else if (RoleBasedAccessHelper.isClient(authentication)) {
            String userUuid = authentication.getName();
            allReservations =
                    reservationService.getAllReservationsByUserUuid(
                            pageNumber, pageSize, UUID.fromString(userUuid));
        } else {
            throw new AccessDeniedException("Access is denied");
        }
        Page<ResponseReservationDto> map = allReservations.map(this::toResponseDto);
        return new PaginatedResponse<>(map);
    }

    @GetMapping("/{id}")
    public ResponseReservationDto findReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        return toResponseDto(reservation);
    }

    private ResponseReservationDto toResponseDto(Reservation reservation) {
        List<ResponseTicketDto> responseTicketDtos = mapTicketsToResponseDtos(reservation.getTickets());

        return new ResponseReservationDto(
                reservation.getId(),
                reservation.getDateOfReservation(),
                reservation.getStatus(),
                reservation.getUser().getFirstName(),
                reservation.getUser().getLastName(),
                responseTicketDtos);
    }

    private List<ResponseTicketDto> mapTicketsToResponseDtos(List<Ticket> tickets) {
        List<ResponseTicketDto> responseTicketDtos = new ArrayList<>();

        for (Ticket ticket : tickets) {
            ResponseTicketDto responseTicketDto = new ResponseTicketDto(
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
            responseTicketDtos.add(responseTicketDto);
        }

        return responseTicketDtos;
    }
    
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseReservationDto createReservation() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userUuid = authentication.getName();
    User user = userService.findByUserUuid(UUID.fromString(userUuid)).orElseThrow();
    Reservation createdReservation = reservationService.createReservation(user);
    return toResponseDto(createdReservation);
  }

  @PutMapping("/confirm/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void confirmReservation(@PathVariable("id") Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (RoleBasedAccessHelper.isClient(authentication)) {
      Reservation reservation = reservationService.findReservationById(id);
      UUID userUuid = UUID.fromString(authentication.getName());
      RoleBasedAccessHelper.checkClientAccess(
          authentication, userUuid, reservation.getUser().getUserUuid());
    }
    reservationService.confirmReservation(id);
  }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (RoleBasedAccessHelper.isClient(authentication)) {
            Reservation reservation = reservationService.findReservationById(id);
            UUID userUuid = UUID.fromString(authentication.getName());
            RoleBasedAccessHelper.checkClientAccess(
                    authentication, userUuid, reservation.getUser().getUserUuid());
        }
        reservationService.deleteReservation(id);
    }
}


