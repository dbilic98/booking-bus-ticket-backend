package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestPlaceDto {

    private final Long id;

    private final String placeName;
}
