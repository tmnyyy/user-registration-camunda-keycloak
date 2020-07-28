package com.example.registrationprocess.services;

import com.example.registrationprocess.services.keycloak.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @created 28/07/2020 - 9:30
 * @project IntelliJ IDEA
 * @author Temnyakov Nikolay
 */

@Slf4j
@Component
public class CancelUserServiceTask implements JavaDelegate {

    private final KeycloakService keycloakService;

    @Autowired
    public CancelUserServiceTask(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String userId = (String) delegateExecution.getVariable("userId");
        keycloakService.cancelUser(userId);
        log.debug("User {} was rejected!", userId);
    }
}
