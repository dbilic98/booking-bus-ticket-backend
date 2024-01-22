package com.bookingbustickets.bookingbustickets.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private Date dateOfDeparture;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonIgnore
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "oneWayRouteId")
    private Route oneWayRoute;

    @ManyToOne
    @JoinColumn(name = "returnRouteId")
    private Route returnRoute;

    @ManyToOne
    @JoinColumn(name = "passenger_category_id")
    private PassengerCategory passengerCategory;
}