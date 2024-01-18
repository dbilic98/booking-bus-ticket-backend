package com.bookingbustickets.bookingbustickets.controller.request;

import com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RequestReservationDto {

    private final Date dateOfReservation;

    private final ReservationStatus status;

    private final Long passengerCategoryId;
}
