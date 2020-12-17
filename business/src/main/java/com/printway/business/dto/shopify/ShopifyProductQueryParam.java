package com.printway.business.dto.shopify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopifyProductQueryParam {
    private int storeId;
    private int limit;
    private String status;
}
