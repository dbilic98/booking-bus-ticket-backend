package com.bookingbustickets.bookingbustickets.controller.response;

import com.bookingbustickets.bookingbustickets.domain.model.PassengerCategory;
import com.bookingbustickets.bookingbustickets.domain.model.Reservation;
import com.bookingbustickets.bookingbustickets.domain.model.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTicketDto {

    private Long id;

    private float price;

    private Date dateOfDeparture;

    @JsonIgnore
    private Reservation reservation;

    @JsonIgnore
    private Route oneWayRoute;

    @JsonIgnore
    private Route returnRoute;

    @JsonIgnore
    private PassengerCategory passengerCategory;
}
