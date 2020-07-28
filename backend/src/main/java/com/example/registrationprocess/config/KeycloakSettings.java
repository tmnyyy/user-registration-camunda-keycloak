package com.example.registrationprocess.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * @created 28/07/2020 - 9:29
 * @project IntelliJ IDEA
 * @author Temnyakov Nikolay
 */
@Component
@Data
@ConfigurationProperties("keycloak")
public class KeycloakSettings {
    private String authServerUrl;
    private String username;
    private String password;
    private String clientId;
}
