package com.example.registrationprocess.services.keycloak.Impl;

import com.example.registrationprocess.dto.UserData;
import com.example.registrationprocess.services.keycloak.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * @created 28/07/2020 - 9:32
 * @project IntelliJ IDEA
 * @author Temnyakov Nikolay
 */

@Slf4j
@Service
public class KeycloakServiceImpl implements KeycloakService {


    private final UsersResource usersResource;

    @Autowired
    public KeycloakServiceImpl(UsersResource usersResource) {
        this.usersResource = usersResource;
    }

    @Override
    public String createUser(UserData userData) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userData.getLogin());
        userRepresentation.setLastName(userData.getLastName());
        userRepresentation.setFirstName(userData.getFirstName());
        userRepresentation.setEmail(userData.getEmail());
        userRepresentation.setEnabled(false); // enable/disable flag

        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Unable to create user: status - " + response.getStatus());
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");


        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userData.getPassword());

        // устанавливаем пароль для пользователя
        usersResource.get(userId).resetPassword(credentialRepresentation);

        // Повторно запрашиваю по id созданного пользователя
        UserRepresentation createdUserRepresentation = getUserRepresentationByLogin(userRepresentation.getUsername());

        if (createdUserRepresentation == null) {
            throw new RuntimeException("Unable to find created user: " + userRepresentation.getUsername());
        }

        return userId;
    }

    /**
     * Поиск пользователя по userName.
     */
    private UserRepresentation getUserRepresentationByLogin(String userName) {
        final List<UserRepresentation> search = usersResource.search(userName, 0, 1);
        if (search.isEmpty()) {
            return null;
        }
        return search.get(0);
    }

    /**
     * Активация пользователя
     * @param userId - id пользователя
     */
    @Override
    public void activateUser(String userId) {

        // fetch an existing user
        UserRepresentation user = usersResource.get(userId).toRepresentation();
        // change user
        user.setEnabled(true);
        // update
        usersResource.get(userId).update(user);
    }

    /**
     * Добавление комментария пользователю
     * @param userId - id пользователя
     */
    @Override
    public void cancelUser(String userId) {
        // todo: можно удалять не нужных пользователей, usersResource.get(userId).remove(); уточнить!

        // fetch an existing user
        UserRepresentation user = usersResource.get(userId).toRepresentation();
        // change user
        user.setAttributes(Collections.singletonMap("comment", Arrays.asList("rejection of a registration")));
        // update
        usersResource.get(userId).update(user);
    }
}
