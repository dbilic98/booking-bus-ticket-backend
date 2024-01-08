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

    private BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Bus createBus(RequestBusDto requestBusDto) {
        int numberOfSeats = requestBusDto.getSeats();
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = new Seat((short) (i + 1));
            seats.add(seat);
        }
        Bus createdBus = new Bus(requestBusDto.getModel(), requestBusDto.getLicensePlate(), seats);
        return busRepository.save(createdBus);
    }
}
