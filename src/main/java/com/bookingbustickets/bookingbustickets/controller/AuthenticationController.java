package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.LoginRequest;
import com.bookingbustickets.bookingbustickets.controller.request.RegisterClientRequest;
import com.bookingbustickets.bookingbustickets.controller.response.LoginResponse;
import com.bookingbustickets.bookingbustickets.controller.response.RegisterClientResponse;
import com.bookingbustickets.bookingbustickets.domain.model.User;
import com.bookingbustickets.bookingbustickets.domain.service.KeycloakService;
import com.bookingbustickets.bookingbustickets.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth-actions")
public class AuthenticationController {

  private final UserService userService;
  private final KeycloakService keycloakService;

  public AuthenticationController(UserService userService, KeycloakService keycloakService) {
    this.userService = userService;
    this.keycloakService = keycloakService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public RegisterClientResponse register(@RequestBody @Validated RegisterClientRequest request) {
    User user = userService.createUser(request);
    return new RegisterClientResponse(user.getUsername());
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Validated LoginRequest request) {
    return keycloakService.login(request);
  }
}
