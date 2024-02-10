package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestBusDto {

    @NotBlank(message = "Model is mandatory")
    @Size(min = 2, max = 50, message = "Model should be at least 2 characters, and should not exceed 50 characters.")
    private final String model;

    @NotBlank(message = "License Plate is mandatory")
    @Size(min = 4, max = 50, message = "License Plate should be at least 4 characters, and should not exceed 50 characters.")
    private final String licensePlate;

    @NotNull(message = "Seats is mandatory")
    @Min(value = 1, message = "Seats must be at least 1")
    @Max(value = 64, message = "Seats cannot exceed 64")
    private final Integer seats;
}
