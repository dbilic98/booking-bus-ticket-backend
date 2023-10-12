package com.bookingbustickets.bookingbustickets.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusStopRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stopOrder;

    @ManyToOne
    @JoinColumn(name = "bus_stop_id")
    private BusStop busStop;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
}
