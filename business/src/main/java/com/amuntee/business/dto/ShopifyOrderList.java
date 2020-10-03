package com.amuntee.business.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ShopifyOrderList {
    List<ShopifyOrder> orders;
}
