package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestPlaceDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponsePlaceDto;
import com.bookingbustickets.bookingbustickets.domain.model.Place;
import com.bookingbustickets.bookingbustickets.domain.repository.PlaceRepository;
import com.bookingbustickets.bookingbustickets.exception.PlaceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Page<Place> getAllPlaces(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return placeRepository.findAll(pageable);
    }
    public List<ResponsePlaceDto> findPlacesByPrefix(String prefix) {
        List<Place> places = placeRepository.findByPlaceNameStartingWith(prefix);
        List<ResponsePlaceDto> responsePlaceDtos = new ArrayList<>();

        for (Place place : places) {
            responsePlaceDtos.add(new ResponsePlaceDto(place.getId(), place.getPlaceName()));
        }

        return responsePlaceDtos;
    }

    public Place findPlaceById(Long id) {
        Optional<Place> placeOptional = placeRepository.findById(id);
        if (placeOptional.isEmpty()) {
            throw new PlaceNotFoundException("Place with ID " + id + " is not found");
        }
        return placeOptional.get();
    }

    public Place createPlace(RequestPlaceDto requestPlaceDto) {
        Place createdPlace = new Place(requestPlaceDto.placeName());
        return placeRepository.save(createdPlace);
    }

    public Place updatePlace(Long id, RequestPlaceDto requestPlaceDto) {
        Place placeToUpdate = findPlaceById(id);
        placeToUpdate.setPlaceName(requestPlaceDto.placeName());
        return placeRepository.save(placeToUpdate);
    }

    public void deletePlace(Long id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
        } else {
            throw new PlaceNotFoundException("Place with ID " + id + " is not found");
        }
    }
}
