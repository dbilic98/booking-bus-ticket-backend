package com.bookingbustickets.bookingbustickets.domain.model;

import com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateOfReservation;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "passenger_category_id")
    private PassengerCategory passengerCategory;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Reservation(LocalDateTime dateOfReservation, ReservationStatus status, PassengerCategory passengerCategory) {
        this.dateOfReservation = dateOfReservation;
        this.status = status;
        this.passengerCategory = passengerCategory;
    }
}