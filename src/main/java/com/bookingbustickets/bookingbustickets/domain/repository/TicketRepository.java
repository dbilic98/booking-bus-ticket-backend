package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT SUM(t.price) AS total_price " +
            "FROM Ticket t " +
            "WHERE t.reservation.id = :reservationId")
    float getTotalPriceByReservationId(@Param("reservationId") Long reservationId);
}
