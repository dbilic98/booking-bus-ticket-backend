package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Reservation r SET r.status = 'CANCELED' WHERE r.dateOfReservation < CURRENT_TIMESTAMP - 10 AND r.status = 'PENDING'")
    void cancelPendingReservations();
}
