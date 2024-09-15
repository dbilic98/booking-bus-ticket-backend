package com.bookingbustickets.bookingbustickets.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSeatDto {

    private Long id;

    private short seatNumber;
    private boolean isFree;
}
