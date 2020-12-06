package com.printway.business.dto.shopify;

import lombok.Data;

import java.util.List;

@Data
public class ShopifyProducts {
    List<ShopifyProduct> products;
}
