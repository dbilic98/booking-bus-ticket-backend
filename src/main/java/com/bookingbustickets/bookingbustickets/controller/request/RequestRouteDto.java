package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RequestRouteDto {

    private final float basePrice;

    private final BigDecimal totalDistance;

    private final long startPlaceId;

    private final long endPlaceId;
}
