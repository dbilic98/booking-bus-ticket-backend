package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RequestTicketDto {

    private final float price;

    private final Date dateOfDeparture;

    private final Long reservationId;

    private final Long oneWayRouteId;

    private final Long returnRouteId;

    private final Long passengerCategoryId;
}
