package com.bookingbustickets.bookingbustickets.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTicketDto {

    private Long id;

    private float price;

    private long oneWayScheduleId;

    private Long returnScheduleId;

    private long reservationId;

    private long oneWayRouteId;

    private Long returnRouteId;

    private long passengerCategoryId;
}
