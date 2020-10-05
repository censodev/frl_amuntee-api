package com.printway.business.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RevenueSummaryStatistic {
    private Integer year;
    private Integer month;
    private Integer ordersCount;
    private Double subTotalPrice;
    private Double totalPrice;
}
