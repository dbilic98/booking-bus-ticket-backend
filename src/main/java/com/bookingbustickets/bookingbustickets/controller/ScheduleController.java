package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestScheduleDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseScheduleDto;
import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import com.bookingbustickets.bookingbustickets.domain.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public PaginatedResponse<ResponseScheduleDto> getSchedules (
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {
        Page<Schedule> allSchedules = scheduleService.getAllSchedules(pageNumber, pageSize);
        Page<ResponseScheduleDto> map = allSchedules.map(this::toResponseDto);
        return new PaginatedResponse<>(map);
    }
    private ResponseScheduleDto toResponseDto(Schedule schedule) {
        return new ResponseScheduleDto(
                schedule.getId(),
                schedule.getScheduleDate(),
                schedule.getDepartureTime(),
                schedule.getArrivalTime(),
                schedule.getRoute().getId(),
                schedule.getBus().getCompany().getCompanyName());
    }
    @GetMapping("/{id}")
    public ResponseScheduleDto findScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.findScheduleById(id);
        return new ResponseScheduleDto(
                schedule.getId(),
                schedule.getScheduleDate(),
                schedule.getDepartureTime(),
                schedule.getArrivalTime(),
                schedule.getRoute().getId(),
                schedule.getBus().getCompany().getCompanyName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseScheduleDto createSchedule(@Valid @RequestBody RequestScheduleDto requestScheduleDto) {
        Schedule createdSchedule = scheduleService.createSchedule(requestScheduleDto);
        return new ResponseScheduleDto(
                createdSchedule.getId(),
                createdSchedule.getScheduleDate(),
                createdSchedule.getDepartureTime(),
                createdSchedule.getArrivalTime(),
                createdSchedule.getRoute().getId(),
                createdSchedule.getBus().getCompany().getCompanyName());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseScheduleDto updateSchedule(@Valid @PathVariable("id") Long id, @RequestBody RequestScheduleDto requestScheduleDto) {
        Schedule updatedSchedule = scheduleService.updateSchedule(id, requestScheduleDto);
        return new ResponseScheduleDto(
                updatedSchedule.getId(),
                updatedSchedule.getScheduleDate(),
                updatedSchedule.getDepartureTime(),
                updatedSchedule.getArrivalTime(),
                updatedSchedule.getRoute().getId(),
                updatedSchedule.getBus().getCompany().getCompanyName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.deleteSchedule(id);
    }
}