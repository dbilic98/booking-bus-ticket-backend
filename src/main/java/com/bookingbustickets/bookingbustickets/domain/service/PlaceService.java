package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestPlaceDto;
import com.bookingbustickets.bookingbustickets.domain.model.Place;
import com.bookingbustickets.bookingbustickets.domain.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceService {

    public final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Place findPlaceById(Long id) {
        Optional<Place> optionalPlace = placeRepository.findById(id);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Place ID is not found!");
        }
        return optionalPlace.get();
    }

    public Place createPlace(RequestPlaceDto requestPlaceDto) {
        Place createdPlace = new Place(requestPlaceDto.getName());
        return placeRepository.save(createdPlace);
    }

    public Place updatePlace(Long placeId, RequestPlaceDto requestPlaceDto) {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Place with ID " + placeId + "' does not exist");
        }
        Place updatedPlace = optionalPlace.get();
        updatedPlace.setName(requestPlaceDto.getName());
        return placeRepository.save(updatedPlace);
    }

    public void deletePlace(Long placeId) {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (optionalPlace.isEmpty()) {
            throw new RuntimeException("Place with ID" + placeId + "does not exists");
        }
        placeRepository.deleteById(placeId);
    }

    public Page<Place> getPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return placeRepository.findAll(pageRequest);
    }


}
