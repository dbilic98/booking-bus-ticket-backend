package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestTicketDto {

    private final float price;

    private final long oneWayScheduleId;

    private final Long returnScheduleId;

    private final long reservationId;

    private final long oneWayRouteId;

    private final Long returnRouteId;

    private final long passengerCategoryId;
}
