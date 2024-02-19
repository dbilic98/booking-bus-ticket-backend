package com.bookingbustickets.bookingbustickets.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private float basePrice;

    private BigDecimal totalDistance;

    @ManyToOne
    @JoinColumn(name = "start_place_id")
    private Place startPlace;

    @ManyToOne
    @JoinColumn(name = "end_place_id")
    private Place endPlace;

    @OneToMany(mappedBy = "route")
    private List<Schedule> scheduleList;

    public Route(float basePrice, BigDecimal totalDistance, Place startPlace, Place endPlace) {
        this.basePrice = basePrice;
        this.totalDistance = totalDistance;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
    }
}
