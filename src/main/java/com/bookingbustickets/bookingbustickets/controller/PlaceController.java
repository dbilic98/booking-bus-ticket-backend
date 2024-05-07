package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestPlaceDto;
import com.bookingbustickets.bookingbustickets.controller.response.ResponsePlaceDto;
import com.bookingbustickets.bookingbustickets.domain.model.Place;
import com.bookingbustickets.bookingbustickets.domain.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<ResponsePlaceDto> findAllPlaces(){
        List<Place> placeList = placeService.findAllPlaces();
        List<ResponsePlaceDto> responsePlaceDtoList = new ArrayList<>();

        for(Place place : placeList){
            ResponsePlaceDto responsePlaceDto = new ResponsePlaceDto(
                    place.getId(),
                    place.getPlaceName()
            );
            responsePlaceDtoList.add(responsePlaceDto);
        }
        return responsePlaceDtoList;
    }

    @GetMapping("/{id}")
    public ResponsePlaceDto findPlaceById(@PathVariable("id") Long id) {
        Place place = placeService.findPlaceById(id);
        return new ResponsePlaceDto(
                place.getId(),
                place.getPlaceName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePlaceDto createPlace(@Valid @RequestBody RequestPlaceDto requestPlaceDto) {
        Place createdPlace = placeService.createPlace(requestPlaceDto);
        return new ResponsePlaceDto(
                createdPlace.getId(),
                createdPlace.getPlaceName());
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponsePlaceDto updatePlace(@Valid @PathVariable("id") Long id, @RequestBody RequestPlaceDto requestPlaceDto) {
        Place updatedPlace = placeService.updatePlace(id, requestPlaceDto);
        return new ResponsePlaceDto(
                updatedPlace.getId(),
                updatedPlace.getPlaceName());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlace(@PathVariable("id") Long id) {
        placeService.deletePlace(id);
    }
}
