package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestPassengerCategoryDto {

    @NotBlank(message = "Category name is mandatory")
    @Size(min = 2, max = 50, message = "Category name should be at least 2 characters, and should not exceed 50 characters.")
    private final String categoryName;

    @NotNull(message = "Discount percentage is mandatory")
    @DecimalMin(value = "0.0", message = "Discount percentage must be greater than or equal to 0.0")
    @DecimalMax(value = "100.0", message = "Discount percentage cannot exceed 100.0")
    private final float discountPercentage;
}
