package com.printway.business.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierStoreRequest {
    private String code;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Integer status;
}
