package com.printway.business.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductStoreRequest {
    private String code;
    private String name;
    private String type;
    private Double baseCost;
    private Integer supplierCode;
    private String shippingTime;
    private String processingTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
    private Integer status;
}
