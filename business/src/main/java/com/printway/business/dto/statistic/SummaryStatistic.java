package com.printway.business.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SummaryStatistic {
    private Integer year;
    private Integer month;
    private Double revenue;
    private Double marketingFee;
    private Double storeFee;
    private Double baseCostFee;
}
