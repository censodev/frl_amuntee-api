package com.printway.business.dto.statistic;

import lombok.Data;

@Data
public class ProductDesignStatistic {
    private String productName;
    private String sku;
    private String sellerName;
    private String productPicture;
    private Integer productQuantity;
    private Double revenue;
}
