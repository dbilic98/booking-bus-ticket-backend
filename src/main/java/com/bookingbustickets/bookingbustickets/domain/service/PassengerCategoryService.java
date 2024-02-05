package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestPassengerCategoryDto;
import com.bookingbustickets.bookingbustickets.domain.model.PassengerCategory;
import com.bookingbustickets.bookingbustickets.domain.repository.PassengerCategoryRepository;
import com.bookingbustickets.bookingbustickets.exception.PassengerCategoryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerCategoryService {

    private final PassengerCategoryRepository passengerCategoryRepository;

    public PassengerCategoryService(PassengerCategoryRepository passengerCategoryRepository) {
        this.passengerCategoryRepository = passengerCategoryRepository;
    }

    public PassengerCategory findPassengerCategoryById(Long id) {
        Optional<PassengerCategory> optionalPassengerCategory = passengerCategoryRepository.findById(id);
        if (optionalPassengerCategory.isEmpty()) {
            throw new PassengerCategoryNotFoundException("Passenger Category with ID " + id + " is not found");
        }
        return optionalPassengerCategory.get();
    }

    public Page<PassengerCategory> getAllPassengerCategories(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return passengerCategoryRepository.findAll(pageable);
    }

    public PassengerCategory createPassengerCategory(RequestPassengerCategoryDto requestPassengerCategoryDto) {
        PassengerCategory createdPassengerCategory = new PassengerCategory(requestPassengerCategoryDto.getCategoryName(), requestPassengerCategoryDto.getDiscountPercentage());
        return passengerCategoryRepository.save(createdPassengerCategory);
    }

    public PassengerCategory updatePassengerCategory(Long id, RequestPassengerCategoryDto requestPassengerCategoryDto) {
        PassengerCategory passengerCategoryToUpdate = findPassengerCategoryById(id);
        passengerCategoryToUpdate.setCategoryName(requestPassengerCategoryDto.getCategoryName());
        passengerCategoryToUpdate.setDiscountPercentage(requestPassengerCategoryDto.getDiscountPercentage());
        return passengerCategoryRepository.save(passengerCategoryToUpdate);
    }

    public void deletePassengerCategory(Long id) {
        if (passengerCategoryRepository.existsById(id)) {
            passengerCategoryRepository.deleteById(id);
        } else {
            throw new PassengerCategoryNotFoundException("Passenger Category with ID " + id + " is not found");
        }
    }
}
