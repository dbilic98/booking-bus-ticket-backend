package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestScheduleDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseScheduleDto;
import com.bookingbustickets.bookingbustickets.domain.model.Bus;
import com.bookingbustickets.bookingbustickets.domain.model.Schedule;
import com.bookingbustickets.bookingbustickets.domain.service.BusService;
import com.bookingbustickets.bookingbustickets.domain.service.ScheduleService;
import com.bookingbustickets.bookingbustickets.util.RoleBasedAccessHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final BusService busService;

    public ScheduleController(ScheduleService scheduleService, BusService busService) {
        this.scheduleService = scheduleService;
        this.busService = busService;
    }

  @GetMapping
  public PaginatedResponse<ResponseScheduleDto> getSchedules(
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "25") int pageSize) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Page<Schedule> allSchedules;
    if (RoleBasedAccessHelper.isCompany(authentication)) {
      UUID companyUuid = UUID.fromString(authentication.getName());
      allSchedules =
          scheduleService.getAllSchedulesByCompanyUuid(pageNumber, pageSize, companyUuid);
    } else if (RoleBasedAccessHelper.isAdmin(authentication)) {
      allSchedules = scheduleService.getAllSchedules(pageNumber, pageSize);
    } else {
      throw new AccessDeniedException("Access denied");
    }
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
                schedule.getBus().getId(),
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
                schedule.getBus().getId(),
                schedule.getBus().getCompany().getCompanyName());
    }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public ResponseScheduleDto createSchedule(
      @Valid @RequestBody RequestScheduleDto requestScheduleDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (RoleBasedAccessHelper.isCompany(authentication)) {
      UUID companyUuid = UUID.fromString(authentication.getName());
      Bus busById = busService.findBusById(requestScheduleDto.busId());
      RoleBasedAccessHelper.checkCompanyAccess(
          authentication, companyUuid, busById.getCompany().getCompanyUuid());
    }
    Schedule createdSchedule = scheduleService.createSchedule(requestScheduleDto);
    return new ResponseScheduleDto(
        createdSchedule.getId(),
        createdSchedule.getScheduleDate(),
        createdSchedule.getDepartureTime(),
        createdSchedule.getArrivalTime(),
        createdSchedule.getRoute().getId(),
        createdSchedule.getBus().getId(),
        createdSchedule.getBus().getCompany().getCompanyName());
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public ResponseScheduleDto updateSchedule(
      @Valid @PathVariable("id") Long id, @RequestBody RequestScheduleDto requestScheduleDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (RoleBasedAccessHelper.isCompany(authentication)) {
      UUID companyUuid = UUID.fromString(authentication.getName());
      Bus busById = busService.findBusById(requestScheduleDto.busId());
      RoleBasedAccessHelper.checkCompanyAccess(
          authentication, companyUuid, busById.getCompany().getCompanyUuid());
    }
    Schedule updatedSchedule = scheduleService.updateSchedule(id, requestScheduleDto);
    return new ResponseScheduleDto(
        updatedSchedule.getId(),
        updatedSchedule.getScheduleDate(),
        updatedSchedule.getDepartureTime(),
        updatedSchedule.getArrivalTime(),
        updatedSchedule.getRoute().getId(),
        updatedSchedule.getBus().getId(),
        updatedSchedule.getBus().getCompany().getCompanyName());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public void deleteSchedule(@PathVariable("id") Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (RoleBasedAccessHelper.isCompany(authentication)) {
      UUID companyUuid = UUID.fromString(authentication.getName());
      Schedule scheduleById = scheduleService.findScheduleById(id);
      RoleBasedAccessHelper.checkCompanyAccess(
          authentication, companyUuid, scheduleById.getBus().getCompany().getCompanyUuid());
    }
      scheduleService.deleteSchedule(id);
  }
}