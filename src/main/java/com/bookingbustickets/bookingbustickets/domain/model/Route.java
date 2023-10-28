package com.bookingbustickets.bookingbustickets.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String startPoint;

    private String endPoint;

    private BigDecimal price;

    private BigDecimal totalDistance;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;
}
