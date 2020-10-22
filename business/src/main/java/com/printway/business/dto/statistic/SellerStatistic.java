package com.printway.business.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SellerStatistic {
    private String name;
    private String team;
    private Integer orderCount;
    private Integer productQuantity;
    private Double revenue;
    private Double marketingFee;
    private Double baseCostFee;
    private Double storeFee;
    private Double netProfit;
    private Double bonusProfit;
    private Double bonusSale;
    private Double sharedProfit;
}
