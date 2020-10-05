package com.printway.business.dto.shopify;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ShopifyOrderList {
    List<ShopifyOrder> orders;
}
