package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestBusDto;
import com.bookingbustickets.bookingbustickets.domain.model.Bus;
import com.bookingbustickets.bookingbustickets.domain.model.Company;
import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import com.bookingbustickets.bookingbustickets.domain.repository.BusRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.CompanyRepository;
import com.bookingbustickets.bookingbustickets.domain.repository.SeatRepository;
import com.bookingbustickets.bookingbustickets.exception.BusNotFoundException;
import com.bookingbustickets.bookingbustickets.exception.CompanyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepository;

    private final CompanyRepository companyRepository;

    private final SeatRepository seatRepository;

    public BusService(BusRepository busRepository, CompanyRepository companyRepository, SeatRepository seatRepository) {
        this.busRepository = busRepository;
        this.companyRepository = companyRepository;
        this.seatRepository = seatRepository;
    }

    public Page<Bus> getAllBuses(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return busRepository.findAll(pageable);
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

    public int getNumberOfSeats(Long busId) {
        Optional<Bus> optionalBus = busRepository.findById(busId);
        if (optionalBus.isEmpty()) {
            throw new BusNotFoundException("Bus with the given ID is not found");
        }
        return seatRepository.countByBusId(busId);
    }
     public Bus findBusById(Long id) {
        Optional<Bus> busOptional = busRepository.findById(id);
        if(busOptional.isEmpty()) {
            throw new BusNotFoundException("Bus with ID " + id + "is not found");
        }
        return busOptional.get();
     }

    public Bus updateBus(Long id, RequestBusDto requestBusDto) {
        Bus busToUpdate = findBusById(id);
        busToUpdate.setModel(requestBusDto.getModel());
        busToUpdate.setLicensePlate(requestBusDto.getLicensePlate());
        //busToUpdate.setSeats(requestBusDto.getSeats());
        return  busRepository.save(busToUpdate);
    }

    public void deleteBus(Long id) {
        if(busRepository.existsById(id)) {
            busRepository.deleteById(id);
        } else {
            throw new BusNotFoundException("Bus with ID " + id + "is not found");
        }
    }

}
