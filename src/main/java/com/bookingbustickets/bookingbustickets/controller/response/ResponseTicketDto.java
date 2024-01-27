package com.bookingbustickets.bookingbustickets.controller.response;

import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTicketDto {

    private Long id;

    private float price;

    private long scheduleDateId;

    private long reservationId;

    private long oneWayRouteId;

    @JsonIgnore
    private Route returnRouteId;

    private long passengerCategoryId;
}
