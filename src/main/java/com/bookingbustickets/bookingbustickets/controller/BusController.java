package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestBusDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseBusDto;
import com.bookingbustickets.bookingbustickets.domain.model.Bus;
import com.bookingbustickets.bookingbustickets.domain.service.BusService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public PaginatedResponse<ResponseBusDto> getBuses (
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {
        Page<Bus> allBuses = busService.getAllBuses(pageNumber, pageSize);
        Page<ResponseBusDto> map = allBuses.map(this::toResponseDto);
        return new PaginatedResponse<>(map);
    }
    private ResponseBusDto toResponseDto(Bus bus) {
        return new ResponseBusDto(
                bus.getId(),
                bus.getModel(),
                bus.getLicensePlate(),
                bus.getCompany().getId(),
                bus.getSeats());
    }
    @PostMapping
    public void createBus(@RequestBody @Valid RequestBusDto requestBusDto) {
        busService.createBus(requestBusDto);
    }

    @GetMapping("/{busId}/seats")
    public int getNumberOfSeats(@PathVariable Long busId) {
        return busService.getNumberOfSeats(busId);
    }

    @GetMapping("/{id}")
    public ResponseBusDto findBusById(@PathVariable Long id) {
        Bus bus = busService.findBusById(id);
        return new ResponseBusDto(
                bus.getId(),
                bus.getModel(),
                bus.getLicensePlate(),
                bus.getCompany().getId(),
                bus.getSeats());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseBusDto updateBus(@Valid @PathVariable("id") Long id, @RequestBody RequestBusDto requestBusDto) {
        Bus updatedBus = busService.updateBus(id, requestBusDto);
        return new ResponseBusDto(
                updatedBus.getId(),
                updatedBus.getModel(),
                updatedBus.getLicensePlate(),
                updatedBus.getCompany().getId(),
                updatedBus.getSeats());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBus(@PathVariable("id") Long id) {
        busService.deleteBus(id);
    }

}
