package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopifyOrderProduct {
    @JsonProperty("title")
    private String title;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price_set")
    private ShopifyProductPriceSet priceSet;
}
