package com.bookingbustickets.bookingbustickets.domain.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  private String username;
  private UUID userUuid;

  @OneToMany(mappedBy = "user")
  private List<Reservation> reservations = new ArrayList<>();

  public User(String firstName, String lastName, String username, UUID userUuid) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.userUuid = userUuid;
  }
}
