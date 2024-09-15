package com.bookingbustickets.bookingbustickets.controller.request;

import jakarta.validation.constraints.*;

public record RegisterClientRequest(
    @NotNull(message = "First name cannot be null")
        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "Max value for first name is 50 characters")
        String firstName,
    @NotNull(message = "Last name cannot be null")
        @NotBlank(message = "Last name cannot be blank")
        @Size(max = 50, message = "Max value for last name is 50 characters")
        String lastName,
    @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be blank")
        @Size(max = 50, message = "Max value for username is 50 characters")
        String username,
    @NotNull(message = "Password can not be null")
        @Size(min = 8, message = "Password must have at least 8 characters")
        String password) {}
