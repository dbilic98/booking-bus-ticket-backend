package com.bookingbustickets.bookingbustickets.controller.response;

import com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseReservationDto {

    private Long id;

    private LocalDateTime dateOfReservation;

    private ReservationStatus status;

    private List<ResponseTicketDto> ticketList;

    public ResponseReservationDto(Long id, LocalDateTime dateOfReservation, ReservationStatus status) {
        this.id = id;
        this.dateOfReservation = dateOfReservation;
        this.status = status;
    }
}
