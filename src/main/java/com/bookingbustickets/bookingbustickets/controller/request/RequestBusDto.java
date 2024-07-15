package com.bookingbustickets.bookingbustickets.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
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

    @NotNull(message = "Company id is mandatory")
    private final Long companyId;

    @JsonCreator
    public RequestBusDto(@JsonProperty("model") String model,
                         @JsonProperty("licensePlate") String licensePlate,
                         @JsonProperty("seats") Integer seats,
                         @JsonProperty("companyId") Long companyId) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.seats = seats;
        this.companyId = companyId;
    }
}
