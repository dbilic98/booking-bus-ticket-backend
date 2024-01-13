package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestScheduleDto;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import com.bookingbustickets.bookingbustickets.domain.repository.RouteRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final RouteRepository routeRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, RouteRepository routeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.routeRepository = routeRepository;
    }

    public Schedule findScheduleById(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new RuntimeException("Schedule with ID " + id + " is not found");
        }
        return optionalSchedule.get();
    }

    public Schedule createSchedule(RequestScheduleDto requestScheduleDto) {
        Optional<Route> optionalRoute = routeRepository.findById(requestScheduleDto.getRouteId());
        if (optionalRoute.isEmpty()) {
            throw new RuntimeException("Route with the given ID is not found");
        }
        Schedule createdSchedule = new Schedule(requestScheduleDto.getScheduleDate(), requestScheduleDto.getDepartureTime(), requestScheduleDto.getArrivalTime());
        createdSchedule.setRoute(optionalRoute.get());
        return scheduleRepository.save(createdSchedule);
    }

    public Schedule updateSchedule(Long id, RequestScheduleDto requestScheduleDto) {
        Schedule updatedSchedule = findScheduleById(id);
        updatedSchedule.setScheduleDate(requestScheduleDto.getScheduleDate());
        updatedSchedule.setDepartureTime(requestScheduleDto.getDepartureTime());
        updatedSchedule.setArrivalTime(requestScheduleDto.getArrivalTime());
        Optional<Route> optionalRoute = routeRepository.findById(requestScheduleDto.getRouteId());
        if (optionalRoute.isEmpty()) {
            throw new RuntimeException("Route with the given ID is not found");
        }
        updatedSchedule.setRoute(optionalRoute.get());
        return scheduleRepository.save(updatedSchedule);
    }

    public void deleteSchedule(Long id) {
        try {
            scheduleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Schedule with ID " + id + "does not exist");
        }
    }
}
