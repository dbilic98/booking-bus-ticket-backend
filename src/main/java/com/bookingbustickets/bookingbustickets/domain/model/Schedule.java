package com.bookingbustickets.bookingbustickets.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate scheduleDate;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Schedule(LocalDate scheduleDate, LocalTime departureTime, LocalTime arrivalTime, Route route) {
        this.scheduleDate = scheduleDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.route=route;
    }
}
