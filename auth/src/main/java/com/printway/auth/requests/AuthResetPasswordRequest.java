package com.printway.auth.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResetPasswordRequest {
    private String code;
    private String password;
    private String confirmPassword;
    private String resetCode;
}
