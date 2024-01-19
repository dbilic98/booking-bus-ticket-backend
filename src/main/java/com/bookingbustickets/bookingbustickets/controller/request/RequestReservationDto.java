package com.bookingbustickets.bookingbustickets.controller.request;

import com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RequestReservationDto {

    private final LocalDateTime dateOfReservation;

    private final ReservationStatus status;

    private final Long passengerCategoryId;
}
