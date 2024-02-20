package com.bookingbustickets.bookingbustickets.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long routeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long busId;

    public ResponseScheduleDto(Long id, LocalDate scheduleDate, LocalTime departureTime, LocalTime arrivalTime) {
        this.id = id;
        this.scheduleDate = scheduleDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}

