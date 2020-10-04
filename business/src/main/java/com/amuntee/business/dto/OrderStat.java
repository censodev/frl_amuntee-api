package com.amuntee.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderStat {
    private Integer year;
    private Integer month;
    private Integer ordersCount;
    private Double subTotalPrice;
    private Double totalPrice;
}
