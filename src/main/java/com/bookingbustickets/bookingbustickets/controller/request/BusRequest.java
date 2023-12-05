package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.Getter;

@Getter
public class BusRequest {

    private final String model;

    private final String licensePlate;

    private final Integer seats;


    public BusRequest(String model, String licensePlate, Integer seats) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.seats = seats;
    }
}
