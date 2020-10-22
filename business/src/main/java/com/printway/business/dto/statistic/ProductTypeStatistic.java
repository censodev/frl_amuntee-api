package com.printway.business.dto.statistic;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductTypeStatistic {
    private String productName;
    private String productCode;
    private Integer orderCount;
    private Integer productQuantity;
    private Double revenue;
}
