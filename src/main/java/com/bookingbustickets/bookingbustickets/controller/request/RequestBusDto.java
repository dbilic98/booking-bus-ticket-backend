package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestBusDto {

    private final String model;

    private final String licensePlate;

    private final Integer seats;
}
