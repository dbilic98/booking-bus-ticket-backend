package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCompanyDto(
        @NotBlank(message = "Company name is mandatory")
        @Size(min = 2, max = 50, message = "Company name should be at least 2 characters, and should not exceed 50 characters.")
        String companyName) {
}
