package com.bookingbustickets.bookingbustickets.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePassengerCategoryDto {

    private Long id;

    private String categoryName;

    private BigDecimal discountPercentage;
}
