package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class RequestScheduleDto {

    @NotNull(message = "Schedule date is mandatory")
    @FutureOrPresent(message = "The date must be today or in the future")
    private final LocalDate scheduleDate;

    @NotNull(message = "Departure time is mandatory")
    private final LocalTime departureTime;

    @NotNull(message = "Arrival time is mandatory")
    private final LocalTime arrivalTime;

    private final Long routeId;
}
