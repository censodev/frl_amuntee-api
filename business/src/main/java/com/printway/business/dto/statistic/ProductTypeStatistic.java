package com.printway.business.dto.statistic;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductTypeStatistic {
    private String title;
    private Integer orderCount;
    private Integer productQuantity;
    private Double revenue;
}
