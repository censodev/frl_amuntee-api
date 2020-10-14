package com.printway.business.dto.statistic;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SpecificStatistic {
    private Integer year;
    private Integer month;
    private String title;
    private Integer productQuantity;
    private Double revenue;
    private Integer orderCount;
}
