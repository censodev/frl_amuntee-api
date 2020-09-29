package com.amuntee.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductShopify {
    @JsonProperty("sku")
    private String sku;

    @JsonProperty("quantity")
    private int quantity;
}
