package com.printway.business.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreStoreRequest {
    private String name;
    private String host;
    private String apiKey;
    private String password;
    private Integer status;
}
