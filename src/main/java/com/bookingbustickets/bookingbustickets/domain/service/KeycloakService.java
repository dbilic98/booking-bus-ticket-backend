package com.bookingbustickets.bookingbustickets.domain.service;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import com.bookingbustickets.bookingbustickets.configuration.KeycloakConfigurationProperties;
import com.bookingbustickets.bookingbustickets.controller.request.LoginRequest;
import com.bookingbustickets.bookingbustickets.controller.request.RegisterClientRequest;
import com.bookingbustickets.bookingbustickets.controller.request.RequestCompanyDto;
import com.bookingbustickets.bookingbustickets.controller.response.LoginResponse;
import com.bookingbustickets.bookingbustickets.domain.enumeration.Role;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakService {

  private final Keycloak keycloak;
  private final KeycloakConfigurationProperties keycloakConfigurationProperties;

  public KeycloakService(
      Keycloak keycloak, KeycloakConfigurationProperties keycloakConfigurationProperties) {
    this.keycloak = keycloak;
    this.keycloakConfigurationProperties = keycloakConfigurationProperties;
  }

  public void registerClientUserOnKeycloak(
      RegisterClientRequest registerClientRequest, String userId) {
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setType("password");
    credentialRepresentation.setValue(registerClientRequest.password());
    credentialRepresentation.setTemporary(false);

    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(registerClientRequest.username());
    userRepresentation.setEnabled(true);
    userRepresentation.singleAttribute("userUuid", userId);
    userRepresentation.setCredentials(List.of(credentialRepresentation));

    Response response =
        keycloak.realm(keycloakConfigurationProperties.realm()).users().create(userRepresentation);
    if (response.getStatus() == 201) {
      assignRole(response, Role.CLIENT);
    } else {
      throw new RuntimeException();
    }
  }

  public void registerCompanyUserOnKeycloak(RequestCompanyDto requestCompanyDto, String companyUuid) {
    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setType("password");
    credentialRepresentation.setValue("password");
    credentialRepresentation.setTemporary(false);

    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(requestCompanyDto.companyName());
    userRepresentation.setEnabled(true);
    userRepresentation.singleAttribute("companyUuid",companyUuid);
    userRepresentation.setCredentials(List.of(credentialRepresentation));

    Response response =
        keycloak.realm(keycloakConfigurationProperties.realm()).users().create(userRepresentation);
    if (response.getStatus() == 201) {
      assignRole(response, Role.COMPANY);
    } else {
      throw new RuntimeException();
    }
  }

  public LoginResponse login(LoginRequest request) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("client_id", keycloakConfigurationProperties.clientId());
    form.add("client_secret", keycloakConfigurationProperties.clientSecret());
    form.add("grant_type", "password");
    form.add("username", request.username());
    form.add("password", request.password());

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(form, headers);

    ResponseEntity<Map> response =
        restTemplate.exchange(
            "http://localhost:8080/realms/booking-bus-tickets-realm/protocol/openid-connect/token",
            HttpMethod.POST,
            requestEntity,
            Map.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      Map responseBody = response.getBody();
      return new LoginResponse(
          (String) responseBody.get("access_token"), (String) responseBody.get("refresh_token"));
    } else {
      throw new RuntimeException("Failed to authenticate user: " + response.getStatusCode());
    }
  }

  private void assignRole(Response response, Role role) {
    String userId = response.getLocation().getPath().replaceAll(".*/", "");
    RealmResource realmResource = keycloak.realm(keycloakConfigurationProperties.realm());
    UsersResource usersResource = realmResource.users();
    ClientRepresentation clientRepresentation =
        realmResource.clients().findByClientId(keycloakConfigurationProperties.clientId()).get(0);
    RoleRepresentation userClientRole =
        realmResource
            .clients()
            .get(clientRepresentation.getId())
            .roles()
            .get(role.name())
            .toRepresentation();
    UserResource userResource = usersResource.get(userId);
    userResource
        .roles()
        .clientLevel(clientRepresentation.getId())
        .add(Collections.singletonList(userClientRole));
  }
}
