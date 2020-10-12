package com.printway.business.requests;

import lombok.Data;

@Data
public class ProductStoreRequest {
    private String code;
    private String name;
    private String type;
    private Double baseCost;
    private String supplierCode;
    private String shippingTime;
    private String processingTime;
    private Integer status;
}
