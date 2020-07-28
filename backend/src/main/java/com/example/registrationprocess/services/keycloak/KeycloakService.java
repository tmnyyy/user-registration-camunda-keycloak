package com.example.registrationprocess.services.keycloak;

import com.example.registrationprocess.dto.UserData;

/*
 * @created 28/07/2020 - 9:32
 * @project IntelliJ IDEA
 * @author Temnyakov Nikolay
 */
public interface KeycloakService {
    String createUser(UserData userData);

    void activateUser(String userId);

    void cancelUser(String userId);
}
