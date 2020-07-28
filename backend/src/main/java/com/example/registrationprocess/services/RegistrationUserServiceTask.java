package com.example.registrationprocess.services;

/*
 * @created 28/07/2020 - 9:30
 * @project IntelliJ IDEA
 * @author Temnyakov Nikolay
 */

import com.example.registrationprocess.dto.UserData;
import com.example.registrationprocess.services.keycloak.KeycloakService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.camunda.spin.Spin.JSON;

@Slf4j
@Component
public class RegistrationUserServiceTask implements JavaDelegate {

    private final KeycloakService keycloakService;

    @Autowired
    public RegistrationUserServiceTask(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Start Registration User");
        /*
        POST /process-definition/aProcessDefinitionId/start
        POST /process-definition/key/aProcessDefinitionKey/start
        http://localhost:8181/engine-rest/process-definition/key/registration_user_process/start
            {
              "variables": {
                "params" : {
                    "value" : {"login": "Nikolay", "firstName": "Nikolay", "lastName": "Temnyakov","email": "nike@yandex.ru","password": "12345678"},
                    "type": "String"
                }
              },
             "businessKey" : "myBusinessKey"
            }
        */
        String params = (String) delegateExecution.getVariable("params");
        UserData userData = JSON(params).mapTo(UserData.class);

        if (Strings.isNullOrEmpty(userData.getLogin()) ||
                Strings.isNullOrEmpty(userData.getPassword()) ||
                Strings.isNullOrEmpty(userData.getEmail())) {
            log.error("noDataError");
            throw new BpmnError("noDataError");
        }

        String userId = keycloakService.createUser(userData);
        delegateExecution.setVariable("userId", userId);
        log.debug("User {} was registered!", userId);
    }
}

