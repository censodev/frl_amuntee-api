package com.printway.business.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
