package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestBusDto;
import com.bookingbustickets.bookingbustickets.domain.model.Bus;
import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import com.bookingbustickets.bookingbustickets.domain.repository.BusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Bus createBus(RequestBusDto requestBusDto) {
        Bus createdBus = new Bus(requestBusDto.getModel(), requestBusDto.getLicensePlate());
        int numberOfSeats = requestBusDto.getSeats();
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = new Seat((short) (i + 1), createdBus);
            seats.add(seat);
        }
        createdBus.setSeats(seats);
        return busRepository.save(createdBus);
    }
}
