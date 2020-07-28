package com.example.registrationprocess.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @created 28/07/2020 - 9:28
 * @project IntelliJ IDEA
 * @author Temnyakov Nikolay
 */
@Configuration
public class KeycloakConfig {
    private final KeycloakSettings keycloakSettings;

    @Autowired
    public KeycloakConfig(KeycloakSettings keycloakSettings) {
        this.keycloakSettings = keycloakSettings;
    }

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder
                .builder()
                .serverUrl(keycloakSettings.getAuthServerUrl())
                .username(keycloakSettings.getUsername())
                .password(keycloakSettings.getPassword())
                .clientId(keycloakSettings.getClientId())
                .realm("master")
                .grantType("password")
                .build();
    }

    @Bean
    public UsersResource getUsersResource(Keycloak keycloak) {
        return keycloak.realm("master").users();
    }
}
