package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import com.bookingbustickets.bookingbustickets.domain.repository.SeatRepository;
import com.bookingbustickets.bookingbustickets.exception.SeatNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void reserveSeat(Long id) {
        Optional<Seat> optionalSeat = seatRepository.findById(id);
        if (optionalSeat.isEmpty()) {
            throw new SeatNotFoundException("Seat with ID " + id + " is not found");
        }
        Seat selectedSeat = optionalSeat.get();

        if (selectedSeat.isTaken()) {
            throw new IllegalArgumentException("Seat is already taken.");
        }
        selectedSeat.setTaken(true);
        seatRepository.save(selectedSeat);
    }
}

