package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;


public record RequestScheduleDto(
        @NotNull(message = "Schedule date is mandatory") @FutureOrPresent(message = "The date must be today or in the future") LocalDate scheduleDate,
        @NotNull(message = "Departure time is mandatory") LocalTime departureTime,
        @NotNull(message = "Arrival time is mandatory") LocalTime arrivalTime, Long routeId, Long busId) {

}
