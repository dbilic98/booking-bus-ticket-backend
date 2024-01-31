package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("SELECT r FROM Route r WHERE r.startPlace.id = :startPlaceId AND r.endPlace.id = :endPlaceId")
    List<Route> findByStartPlaceAndEndPlace(@Param("startPlaceId") Long startPlaceId, @Param("endPlaceId") Long endPlaceId);
}
