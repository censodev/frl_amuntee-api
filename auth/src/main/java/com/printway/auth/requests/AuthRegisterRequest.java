package com.printway.auth.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String code;
    private String fullname;
    private String phone;
    private String email;
}
