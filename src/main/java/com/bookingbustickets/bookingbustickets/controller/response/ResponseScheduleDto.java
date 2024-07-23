package com.bookingbustickets.bookingbustickets.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime departureTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime arrivalTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long routeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String companyName;

    public ResponseScheduleDto(Long id, LocalDate scheduleDate, LocalTime departureTime, LocalTime arrivalTime, String companyName) {
        this.id = id;
        this.scheduleDate = scheduleDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.companyName = companyName;
    }
}

