package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.scheduleDate > :currentDate OR (s.scheduleDate = :currentDate " + "AND s.departureTime >= :currentTime)")
    List<Schedule> findFutureDates(@Param("currentDate") LocalDate currentDate, @Param("currentTime") LocalTime currentTime);
}
