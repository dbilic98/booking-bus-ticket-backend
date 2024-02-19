package com.bookingbustickets.bookingbustickets.exception;

public class ExpiredReservationException extends RuntimeException {

    public ExpiredReservationException(String message) {
        super(message);
    }
}