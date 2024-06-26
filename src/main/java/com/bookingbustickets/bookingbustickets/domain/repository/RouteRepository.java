package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("SELECT DISTINCT r " +
            "FROM Route r " +
            "JOIN FETCH r.scheduleList s " +
            "WHERE r.startPlace.id = :startPlaceId " +
            "AND r.endPlace.id = :endPlaceId " +
            "AND (s.scheduleDate > CURRENT_DATE OR (s.scheduleDate = :scheduleDate AND s.departureTime > CURRENT_TIME))")

    List<Route> findRoutesBetweenPlacesAndDate(@Param("startPlaceId") Long startPlaceId,
                                               @Param("endPlaceId") Long endPlaceId,
                                               @Param("scheduleDate") LocalDate scheduleDate);
}