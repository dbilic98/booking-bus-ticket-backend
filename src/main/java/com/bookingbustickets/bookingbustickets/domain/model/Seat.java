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
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT")
    private Short seatNumber;

    private boolean isTaken;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Seat(Short seatNumber) {
        this.seatNumber = seatNumber;
    }
}
