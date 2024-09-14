package com.bookingbustickets.bookingbustickets.controller.response;

import com.bookingbustickets.bookingbustickets.domain.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBusDto {

    private Long id;

    private String model;

    private String licensePlate;

    private long companyId;

    private List<Seat> seats;

}
