package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyProductVariant {
    @JsonProperty("id")
    private long id;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("compare_at_price")
    private double compareAtPrice;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("image_id")
    private long imageId;

    @JsonProperty("price")
    private double price;

    @JsonProperty("product_id")
    private long productId;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("title")
    private String title;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("option1")
    private String option1;

    @JsonProperty("option2")
    private String option2;

    @JsonProperty("option3")
    private String option3;
}
