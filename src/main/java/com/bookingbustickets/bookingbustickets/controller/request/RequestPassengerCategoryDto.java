package com.bookingbustickets.bookingbustickets.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestPassengerCategoryDto {

    private final String categoryName;

    private final float discountPercentage;
}
