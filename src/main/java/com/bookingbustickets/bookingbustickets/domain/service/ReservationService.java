package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import com.bookingbustickets.bookingbustickets.domain.repository.ReservationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus.CONFIRMED;
import static com.bookingbustickets.bookingbustickets.domain.enumeration.ReservationStatus.PENDING;

@Service
public class ReservationService {

    private static final long ALLOWED_MINUTES = 10;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation findReservationById(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            throw new RuntimeException("Reservation with ID " + id + " does not exist");
        }
        return optionalReservation.get();
    }

    public Reservation createReservation() {
        Reservation createdReservation = new Reservation(LocalDateTime.now(), PENDING);
        return reservationRepository.save(createdReservation);
    }

    public void confirmReservation(Long id) {
        Reservation reservation = findReservationById(id);
        validateReservation(reservation);
        reservation.setStatus(CONFIRMED);
        reservationRepository.save(reservation);
    }

    public void cancelPendingReservations() {
        reservationRepository.cancelPendingReservations();
    }

    public void deleteReservation(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Reservation with id " + id + "does not exist");
        }
    }

    private void validateReservation(Reservation reservation) {
        if (!reservation.hasAnyTickets()) {
            throw new RuntimeException("Reservation with ID " + reservation.getId() + " does not have any tickets");
        }
        if (!reservation.isPending()) {
            throw new RuntimeException("The reservation status is not pending");
        }
        if (reservation.isOlderThanDefinedThreshold(ALLOWED_MINUTES)) {
            throw new RuntimeException("Reservation is older than " + ALLOWED_MINUTES + " minutes. Can not be confirmed.");
        }
    }

}
