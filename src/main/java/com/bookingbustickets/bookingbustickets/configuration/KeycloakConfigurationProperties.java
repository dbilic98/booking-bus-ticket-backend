package com.bookingbustickets.bookingbustickets.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakConfigurationProperties(
    String realm, String clientId, String clientSecret, String serverUrl) {}
