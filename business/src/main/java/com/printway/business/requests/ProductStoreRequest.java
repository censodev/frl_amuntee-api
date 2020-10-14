package com.printway.business.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.printway.business.models.ProductType;
import com.printway.business.models.Supplier;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductStoreRequest {
    private String code;
    private String name;
    private Double baseCost;
    private String shippingTime;
    private String processingTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
    private Integer status;
    private ProductType type;
    private Supplier supplier;
}
