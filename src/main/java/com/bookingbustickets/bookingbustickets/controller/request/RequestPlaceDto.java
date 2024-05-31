package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestPlaceDto(
        @NotBlank(message = "Place name is mandatory")
        @Size(min = 4, max = 50, message = "Place name should be at least 4 characters, and should not exceed 50 characters.")
        String placeName) {
}
