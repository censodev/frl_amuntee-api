package com.amuntee.auth.requests;

import lombok.Data;

@Data
public class RegisterRequest extends LoginRequest {
    private String code;
    private String confirmPassword;
    private String fullname;
}
