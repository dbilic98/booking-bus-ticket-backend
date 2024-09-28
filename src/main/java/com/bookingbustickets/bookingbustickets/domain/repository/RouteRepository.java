package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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


    @Query("SELECT DISTINCT r " +
            "FROM Route r " +
            "JOIN FETCH r.scheduleList s " +
            "JOIN s.bus b " +
            "JOIN b.company c " +
            "WHERE c.companyUuid = :companyUuid")
    Page<Route> findRoutesByCompanyUuid(@Param("companyUuid") UUID companyUuid, Pageable pageable);
}
