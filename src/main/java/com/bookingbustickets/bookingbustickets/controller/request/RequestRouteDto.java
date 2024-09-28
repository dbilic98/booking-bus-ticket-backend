package com.bookingbustickets.bookingbustickets.controller.request;

import com.bookingbustickets.bookingbustickets.controller.response.ResponseScheduleDto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record RequestRouteDto(
        @NotNull(message = "Base price is mandatory") @DecimalMin(value = "0.0", message = "Base price must be greater than or equal to 0.0") @DecimalMax(value = "100.0", message = "Base price cannot exceed 100.0") float basePrice,
        @NotNull(message = "Total distance is mandatory") @DecimalMin(value = "0.0", message = "Total distance must be greater than or equal to 0.0") @DecimalMax(value = "100000.0", message = "Total distance cannot exceed 100000.0") BigDecimal totalDistance,
        long startPlaceId, long endPlaceId, List<ResponseScheduleDto> scheduleList) {
}
