package com.printway.business.requests;

import lombok.Data;

@Data
public class SupplierStoreRequest {
    private String code;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Integer status;
}
