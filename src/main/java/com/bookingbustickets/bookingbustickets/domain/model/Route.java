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

    private String startPoint;

    private String endPoint;

    private BigDecimal basePrice;

    private BigDecimal totalDistance;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

    public Route(String startPoint, String endPoint, BigDecimal basePrice, BigDecimal totalDistance) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.basePrice = basePrice;
        this.totalDistance = totalDistance;
    }

}
