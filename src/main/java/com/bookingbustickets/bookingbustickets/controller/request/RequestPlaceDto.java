package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.NotBlank;

public class RequestPlaceDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    public RequestPlaceDto() {
    }

    public RequestPlaceDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
