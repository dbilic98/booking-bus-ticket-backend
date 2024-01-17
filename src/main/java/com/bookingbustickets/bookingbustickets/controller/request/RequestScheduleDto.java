package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class RequestScheduleDto {

    private final LocalDate scheduleDate;

    private final LocalTime departureTime;

    private final LocalTime arrivalTime;

    private final Long routeId;
}
