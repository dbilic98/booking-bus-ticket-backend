package com.bookingbustickets.bookingbustickets.controller.response;

import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRouteDto {

    private Long id;

    private float basePrice;

    private BigDecimal totalDistance;

    private long startPlaceId;

    private long endPlaceId;

    private List<Schedule> scheduleList;
}
