package com.example.registrationprocess.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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

    @Bean
    public FilterRegistrationBean processCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
