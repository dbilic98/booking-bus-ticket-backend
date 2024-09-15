package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.response.ResponseSeatDto;
import com.bookingbustickets.bookingbustickets.domain.repository.SeatRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatController {

  private final SeatRepository seatRepository;

  public SeatController(SeatRepository seatRepository) {
    this.seatRepository = seatRepository;
  }

  @GetMapping
  public List<ResponseSeatDto> findSeats(
      @RequestParam("routeId") Long routeId, @RequestParam("scheduleId") Long scheduleId) {
    return seatRepository.findAvailableSeatsByRouteAndSchedule(routeId, scheduleId);
  }
}
