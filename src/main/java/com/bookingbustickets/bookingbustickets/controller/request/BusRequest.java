package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusRequest {

    private final String model;

    private final String licensePlate;

    private final Integer seats;

}
