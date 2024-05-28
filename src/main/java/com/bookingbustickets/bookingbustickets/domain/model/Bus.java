package com.bookingbustickets.bookingbustickets.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String licensePlate;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @OneToMany(mappedBy = "bus")
    private List<Schedule> schedules;

    public Bus(String model, String licensePlate) {
        this.model = model;
        this.licensePlate = licensePlate;
    }
}
