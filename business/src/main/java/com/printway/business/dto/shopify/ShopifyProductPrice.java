package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopifyProductPrice {
    private double amount;

    @JsonProperty("currency_code")
    private String currencyCode;
}
