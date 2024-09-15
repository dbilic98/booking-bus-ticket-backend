package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RegisterClientRequest;
import com.bookingbustickets.bookingbustickets.domain.model.User;
import com.bookingbustickets.bookingbustickets.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final KeycloakService keycloakService;

  public UserService(UserRepository userRepository, KeycloakService keycloakService) {
    this.userRepository = userRepository;
    this.keycloakService = keycloakService;
  }

  public User createUser(RegisterClientRequest request) {
    UUID userUuid = UUID.randomUUID();
    keycloakService.registerClientUserOnKeycloak(request, userUuid.toString());
    Optional<User> optionalUser = userRepository.findByUsername(request.username());
    if (optionalUser.isPresent()) {
      throw new RuntimeException("User with username " + request.username() + " already exists");
    }
    return userRepository.save(
        new User(request.firstName(), request.lastName(), request.username(), userUuid));
  }

    public Optional<User> findByUserUuid(UUID userUuid) {
      return userRepository.findByUserUuid(userUuid);
    }
}
