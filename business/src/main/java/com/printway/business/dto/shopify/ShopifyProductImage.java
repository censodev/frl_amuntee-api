package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopifyProductImage {
    @JsonProperty("id")
    private int id;

    @JsonProperty("position")
    private int position;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("src")
    private String src;

    @JsonProperty("width")
    private int width;

    @JsonProperty("height")
    private int height;
}
