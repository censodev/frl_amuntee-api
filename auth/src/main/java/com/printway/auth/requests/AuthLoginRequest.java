package com.printway.auth.requests;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String username;
    private String password;
}
