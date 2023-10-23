package com.bookingbustickets.bookingbustickets.controller.response;

public class ResponsePlaceDto {

    private final long id;

    private final String name;

    public ResponsePlaceDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
