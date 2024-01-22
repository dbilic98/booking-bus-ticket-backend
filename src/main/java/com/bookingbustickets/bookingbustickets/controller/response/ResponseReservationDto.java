package com.bookingbustickets.bookingbustickets.controller.response;

import com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseReservationDto {

    private Long id;

    private LocalDateTime dateOfReservation;

    private ReservationStatus status;
}
