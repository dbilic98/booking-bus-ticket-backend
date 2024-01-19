package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestReservationDto;
import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import com.bookingbustickets.bookingbustickets.domain.repository.ReservationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public Reservation findReservationById(Long id){
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isEmpty()){
            throw new RuntimeException("Reservation with ID " + id + " does not exist");
        }
        return optionalReservation.get();
    }

    public Reservation createReservation(RequestReservationDto requestReservationDto){
        Reservation createdReservation = new Reservation(requestReservationDto.getDateOfReservation(), requestReservationDto.getStatus());
        return reservationRepository.save(createdReservation);
    }

    public Reservation updateReservation(Long id, RequestReservationDto requestReservationDto){
        Reservation reservationToUpdate = findReservationById(id);
        reservationToUpdate.setDateOfReservation(requestReservationDto.getDateOfReservation());
        reservationToUpdate.setStatus(requestReservationDto.getStatus());
        return reservationRepository.save(reservationToUpdate);
    }

    public void deleteReservation(Long id){
        try {
            reservationRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException("Reservation with id " + id + "does not exist");
        }
    }
}
