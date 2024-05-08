package com.bookingbustickets.bookingbustickets.exception;

public class BusNotFoundException extends RuntimeException{

    public BusNotFoundException(String message){
        super(message);
    }
}
