package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterClientRequest(

        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "Max value for first name is 50 characters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Size(max = 50, message = "Max value for last name is 50 characters")
        String lastName,

        @NotBlank(message = "Username cannot be blank")
        @Size(max = 50, message = "Max value for username is 50 characters")
        String username,
        @NotNull(message = "Password can not be null")
        @Size(min = 8, message = "Password must have at least 8 characters")
        String password) {
}
