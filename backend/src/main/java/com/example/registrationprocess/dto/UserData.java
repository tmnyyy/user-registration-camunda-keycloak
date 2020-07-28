package com.example.registrationprocess.dto;

import lombok.Data;

/*
 * @created 28/07/2020 - 10:50
 * @project IntelliJ IDEA
 * @author Temnyakov Nikolay
 */
@Data
public class UserData {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
