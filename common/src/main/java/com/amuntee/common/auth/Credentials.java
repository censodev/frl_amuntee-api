package com.amuntee.common.auth;

import lombok.Data;

import java.util.List;

@Data
public class Credentials {
    private Long uid;
    private String code;
    private String username;
    private String fullname;
    private List<String> authorities;
}
