package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestScheduleDto;
import com.bookingbustickets.bookingbustickets.domain.model.Bus;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import com.bookingbustickets.bookingbustickets.domain.repository.BusRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.RouteRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.ScheduleRepository;
import com.bookingbustickets.bookingbustickets.exception.RouteNotFoundException;
import com.bookingbustickets.bookingbustickets.exception.ScheduleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final RouteRepository routeRepository;

    private final BusRepository busRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, RouteRepository routeRepository, BusRepository busRepository) {
        this.scheduleRepository = scheduleRepository;
        this.routeRepository = routeRepository;
        this.busRepository = busRepository;
    }

    public Schedule findScheduleById(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ScheduleNotFoundException("Schedule with ID " + id + " is not found");
        }
        return optionalSchedule.get();
    }

    public Schedule createSchedule(RequestScheduleDto requestScheduleDto) {
        Optional<Route> optionalRoute = routeRepository.findById(requestScheduleDto.getRouteId());
        if (optionalRoute.isEmpty()) {
            throw new RouteNotFoundException("Route with the given ID is not found");
        }
        Optional<Bus> optionalBus = busRepository.findById(requestScheduleDto.getBusId());
        if(optionalBus.isEmpty()){
            throw new RuntimeException("Bus with the given ID is not found");
        }
        Schedule createdSchedule = new Schedule(requestScheduleDto.getScheduleDate(), requestScheduleDto.getDepartureTime(), requestScheduleDto.getArrivalTime(), optionalRoute.get(), optionalBus.get());
        return scheduleRepository.save(createdSchedule);
    }

    public Schedule updateSchedule(Long id, RequestScheduleDto requestScheduleDto) {
        Schedule scheduleToUpdate = findScheduleById(id);
        scheduleToUpdate.setScheduleDate(requestScheduleDto.getScheduleDate());
        scheduleToUpdate.setDepartureTime(requestScheduleDto.getDepartureTime());
        scheduleToUpdate.setArrivalTime(requestScheduleDto.getArrivalTime());
        return scheduleRepository.save(scheduleToUpdate);
    }

    public void deleteSchedule(Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
        } else {
            throw new ScheduleNotFoundException("Schedule with ID " + id + " is not found");
        }
    }
}
