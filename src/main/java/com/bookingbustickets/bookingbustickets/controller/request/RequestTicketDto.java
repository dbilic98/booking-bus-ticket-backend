package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestTicketDto {

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0")
    @DecimalMax(value = "100.0", message = "Price cannot exceed 100.0")
    private final float price;

    private final long oneWayScheduleId;

    private final Long returnScheduleId;

    private final long reservationId;

    private final long oneWayRouteId;

    private final Long returnRouteId;

    private final long passengerCategoryId;
}
