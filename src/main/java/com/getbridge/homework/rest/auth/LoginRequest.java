package com.getbridge.homework.rest.auth;

import lombok.Data;

@Data
public class LoginRequest {

    String email;
    String password;
}
