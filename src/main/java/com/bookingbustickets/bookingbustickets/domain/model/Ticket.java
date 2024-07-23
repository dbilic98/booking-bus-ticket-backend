package com.bookingbustickets.bookingbustickets.domain.model;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "one_way_schedule_id")
    private Schedule oneWaySchedule;

    @ManyToOne
    @JoinColumn(name = "return_schedule_id")
    private Schedule returnSchedule;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "one_way_route_id ")
    private Route oneWayRoute;

    @ManyToOne
    @JoinColumn(name = "return_route_id ")
    private Route returnRoute;

    @ManyToOne
    @JoinColumn(name = "passenger_category_id")
    private PassengerCategory passengerCategory;

    @ManyToOne
    @JoinColumn(name = "one_way_seat_id")
    private Seat oneWaySeat;

    @ManyToOne
    @JoinColumn(name = "return_seat_id")
    private Seat returnSeat;
}