package com.bookingbustickets.bookingbustickets.configuration;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
  private final KeycloakConfigurationProperties keycloakConfigurationProperties;

  public KeycloakConfig(KeycloakConfigurationProperties keycloakConfigurationProperties) {
    this.keycloakConfigurationProperties = keycloakConfigurationProperties;
  }

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(keycloakConfigurationProperties.serverUrl())
        .realm(keycloakConfigurationProperties.realm())
        .clientId(keycloakConfigurationProperties.clientId())
        .clientSecret(keycloakConfigurationProperties.clientSecret())
        .grantType("client_credentials")
        .build();
  }
}
