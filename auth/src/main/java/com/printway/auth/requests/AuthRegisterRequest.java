package com.printway.auth.requests;

import lombok.Data;

@Data
public class AuthRegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String code;
    private String fullname;
    private String phone;
    private String email;
}
