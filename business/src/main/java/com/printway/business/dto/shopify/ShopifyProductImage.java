package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyProductImage {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("position")
    private Integer position;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("src")
    private String src;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("attachment")
    private String attachment;

    @JsonProperty("filename")
    private String filename;
}
