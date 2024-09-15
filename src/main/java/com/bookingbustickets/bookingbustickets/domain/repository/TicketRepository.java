package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT SUM(t.price) AS total_price " +
            "FROM Ticket t " +
            "WHERE t.reservation.id = :reservationId")
    float getTotalPriceByReservationId(@Param("reservationId") Long reservationId);
    @Query("SELECT t FROM Ticket t JOIN t.oneWaySeat s JOIN s.bus b JOIN b.company c WHERE c.companyUuid = :companyUuid")
    Page<Ticket> findAllByCompanyUuid(@Param("companyUuid") UUID companyUuid, Pageable pageable);
    @Query("SELECT t FROM Ticket t JOIN t.reservation r JOIN r.user u WHERE u.userUuid = :userUuid")
    Page<Ticket> findAllByUserUuid(UUID userUuid, Pageable pageable);
}
