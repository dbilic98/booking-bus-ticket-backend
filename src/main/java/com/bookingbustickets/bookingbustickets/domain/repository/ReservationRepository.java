package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Reservation r SET r.status = 'CANCELED' WHERE r.dateOfReservation < :thresholdDatetime AND r.status = 'PENDING'")
    void cancelPendingReservations(@Param("thresholdDatetime") LocalDateTime thresholdDatetime);

    @Query("SELECT r FROM Reservation r " +
            "JOIN r.tickets t " +
            "JOIN t.oneWaySchedule s " +
            "JOIN s.bus b " +
            "WHERE b.company.companyUuid = :companyUuid")
    Page<Reservation> findAllByCompanyId(@Param("companyUuid") UUID companyUuid, Pageable pageable);

    Page<Reservation> findAllByUserUserUuid(UUID userUuid, Pageable pageable);
}
