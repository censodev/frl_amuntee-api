package com.printway.business.dto.statistic;

import lombok.Data;

@Data
public class SupplierStatistic {
    private String date;
    private String orderId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double baseCost;
    private String supplierName;
    private String sku;
}
