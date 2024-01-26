package com.bookingbustickets.bookingbustickets.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private float price;

    private Date dateOfDeparture;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "one_way_route_id ")
    private Route oneWayRoute;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "return_route_id ")
    private Route returnRoute;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "passenger_category_id")
    private PassengerCategory passengerCategory;

    public void calculatePrice(Route oneWayRoute, Route returnRoute, PassengerCategory passengerCategory) {
        float basePrice;
        if (returnRoute != null) {
            basePrice = returnRoute.getBasePrice() * 2;
        } else {
            basePrice = oneWayRoute.getBasePrice();
        }
        float discountPercentage = passengerCategory.getDiscountPercentage();

        this.price = basePrice * discountPercentage;
    }
}