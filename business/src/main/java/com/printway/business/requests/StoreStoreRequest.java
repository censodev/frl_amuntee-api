package com.printway.business.requests;

import lombok.Data;

@Data
public class StoreStoreRequest {
    private String name;
    private String host;
    private String apiKey;
    private String password;
    private Integer status;
}
