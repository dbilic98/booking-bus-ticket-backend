package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestBusDto;
import com.bookingbustickets.bookingbustickets.domain.model.Bus;
import com.bookingbustickets.bookingbustickets.domain.model.Company;
import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import com.bookingbustickets.bookingbustickets.domain.repository.BusRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.CompanyRepository;
import com.bookingbustickets.bookingbustickets.exception.CompanyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepository;

    private final CompanyRepository companyRepository;

    public BusService(BusRepository busRepository, CompanyRepository companyRepository) {
        this.busRepository = busRepository;
        this.companyRepository = companyRepository;
    }

    public void createBus(RequestBusDto requestBusDto) {
        Optional<Company> optionalCompany = companyRepository.findById(requestBusDto.getCompanyId());
        if (optionalCompany.isEmpty()) {
            throw new CompanyNotFoundException("Company with the given ID is not found");
        }
        Bus createdBus = new Bus(requestBusDto.getModel(), requestBusDto.getLicensePlate(), optionalCompany.get());
        int numberOfSeats = requestBusDto.getSeats();
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = new Seat((short) (i + 1), createdBus);
            seats.add(seat);
        }
        createdBus.setSeats(seats);
        busRepository.save(createdBus);
    }
}
