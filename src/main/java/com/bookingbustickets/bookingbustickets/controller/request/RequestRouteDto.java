package com.bookingbustickets.bookingbustickets.controller.request;

import com.bookingbustickets.bookingbustickets.controller.response.ResponseScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RequestRouteDto {

    private final float basePrice;

    private final BigDecimal totalDistance;

    private final long startPlaceId;

    private final long endPlaceId;

    private final List<ResponseScheduleDto> scheduleList;
}
