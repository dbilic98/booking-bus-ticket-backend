package com.bookingbustickets.bookingbustickets.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRouteDto {

    private Long id;

    private String startPoint;

    private String endPoint;

    private BigDecimal basePrice;

    private BigDecimal totalDistance;

}
