package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyProductImage {
    @JsonProperty("id")
    private long id;

    @JsonProperty("position")
    private int position;

    @JsonProperty("product_id")
    private long productId;

    @JsonProperty("src")
    private String src;

    @JsonProperty("width")
    private int width;

    @JsonProperty("height")
    private int height;

    @JsonProperty("attachment")
    private String attachment;
}
