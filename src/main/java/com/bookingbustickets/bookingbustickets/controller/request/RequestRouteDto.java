package com.bookingbustickets.bookingbustickets.controller.request;

import com.bookingbustickets.bookingbustickets.controller.response.ResponseScheduleDto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RequestRouteDto {

    @NotNull(message = "Base price is mandatory")
    @DecimalMin(value = "0.0", message = "Base price must be greater than or equal to 0.0")
    @DecimalMax(value = "100.0", message = "Base price cannot exceed 100.0")
    private final float basePrice;

    @NotNull(message = "Total distance is mandatory")
    @DecimalMin(value = "0.0", message = "Total distance must be greater than or equal to 0.0")
    @DecimalMax(value = "100000.0", message = "Total distance cannot exceed 100000.0")
    private final BigDecimal totalDistance;

    private final long startPlaceId;

    private final long endPlaceId;

    private final List<ResponseScheduleDto> scheduleList;
}
