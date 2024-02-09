package com.bookingbustickets.bookingbustickets.controller.response;

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

    private List<ResponseScheduleDto> scheduleList;

    public ResponseRouteDto(long id, float basePrice, BigDecimal totalDistance, long startPlaceId, long endPlaceId){
        this.id = id;
        this.basePrice = basePrice;
        this.totalDistance = totalDistance;
        this.startPlaceId = startPlaceId;
        this.endPlaceId = endPlaceId;
    }
}
