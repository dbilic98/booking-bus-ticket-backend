package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import com.bookingbustickets.bookingbustickets.domain.repository.SeatRepository;
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
            throw new RuntimeException("Seat with ID " + id + " does not exist");
        }
        Seat selectedSeat = optionalSeat.get();
        selectedSeat.markAsTaken();

        seatRepository.save(selectedSeat);
    }
}

