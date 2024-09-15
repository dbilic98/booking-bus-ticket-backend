package com.bookingbustickets.bookingbustickets.domain.repository;

import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import jakarta.ws.rs.QueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s JOIN s.bus b JOIN b.company c WHERE c.companyUuid = :companyUuid ")
    Page<Schedule> findAllByCompanyUuid(Pageable pageable, @Param("companyUuid") UUID companyUuid);
}
