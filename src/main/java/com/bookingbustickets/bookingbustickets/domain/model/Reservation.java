package com.bookingbustickets.bookingbustickets.domain.model;

import com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus.PENDING;

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

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Reservation(LocalDateTime dateOfReservation, ReservationStatus status) {
        this.dateOfReservation = dateOfReservation;
        this.status = status;
    }

    public boolean hasAnyTickets() {
        return !tickets.isEmpty();
    }

    public boolean isPending() {
        return status == PENDING;
    }

    public boolean isOlderThanDefinedThreshold(long thresholdMinutes) {
        LocalDateTime reservationTime = dateOfReservation;
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeDifference = Duration.between(reservationTime, currentTime);
        return timeDifference.toMinutes() > thresholdMinutes;
    }

}