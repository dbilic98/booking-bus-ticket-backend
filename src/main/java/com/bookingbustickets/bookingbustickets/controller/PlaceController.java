package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestPlaceDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponsePlaceDto;
import com.bookingbustickets.bookingbustickets.domain.model.Place;
import com.bookingbustickets.bookingbustickets.domain.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/{id}")
    public ResponsePlaceDto findPlaceById(@PathVariable long id) {
        Place place = placeService.findPlaceById(id);
        return new ResponsePlaceDto(place.getId(), place.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePlaceDto createPlace(@Valid @RequestBody RequestPlaceDto requestPlaceDto) {
        Place createdPlace = placeService.createPlace(requestPlaceDto);
        return new ResponsePlaceDto(createdPlace.getId(), createdPlace.getName());
    }

    @PutMapping("/{id}")
    public ResponsePlaceDto updatePlace(@Valid @PathVariable("id") Long placeId, @RequestBody RequestPlaceDto requestPlaceDto) {
        Place updatedPlace = placeService.updatePlace(placeId, requestPlaceDto);
        return new ResponsePlaceDto(updatedPlace.getId(), updatedPlace.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlace(@PathVariable("id") Long placeId) {
        placeService.deletePlace(placeId);
    }

    @GetMapping
    public PaginatedResponse<ResponsePlaceDto> getAllPlaces(@RequestParam(name = "page") int pageNumber, @RequestParam(name = "size") int pageSize) {
        Page<Place> placePage = placeService.getPage(pageNumber - 1, pageSize);
        List<ResponsePlaceDto> pageResponseContent = new ArrayList<>();
        for (Place place : placePage) {
            ResponsePlaceDto responsePlaceDto = new ResponsePlaceDto(place.getId(), place.getName());
            pageResponseContent.add(responsePlaceDto);
        }
        PageImpl<ResponsePlaceDto> responsePlaceDtosPage = new PageImpl<>(pageResponseContent, placePage.getPageable(), placePage.getTotalElements());
        return new PaginatedResponse<>(responsePlaceDtosPage);
    }
}