package com.bookingbustickets.bookingbustickets.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseScheduleDto {

    private Long id;

    private LocalDate scheduleDate;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private Long routeId;
}

