package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    int countByBusId(Long busId);

    @Query("SELECT s " +
            "FROM Seat s " +
            "JOIN s.bus b " +
            "JOIN b.schedules sc " +
            "LEFT JOIN Ticket t1 ON s.id = t1.oneWaySeat.id AND sc.id = t1.oneWaySchedule.id " +
            "LEFT JOIN Ticket t2 ON s.id = t2.returnSeat.id AND sc.id = t2.returnSchedule.id " +
            "LEFT JOIN t1.reservation r1 " +
            "LEFT JOIN t2.reservation r2 " +
            "WHERE sc.route.id = :routeId " +
            "AND sc.id = :scheduleId " +
            "AND (t1.id IS NULL OR r1.status = 'CANCELED') " +
            "AND (t2.id IS NULL OR r2.status = 'CANCELED')")
    List<Seat> findAvailableSeatsByRouteAndSchedule(@Param("routeId") Long routeId,
                                                    @Param("scheduleId") Long scheduleId);

}

