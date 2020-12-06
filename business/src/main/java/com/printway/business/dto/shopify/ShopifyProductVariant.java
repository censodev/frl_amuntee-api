package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopifyProductVariant {
    @JsonProperty("id")
    private int id;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("compare_at_price")
    private double compareAtPrice;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("image_id")
    private int imageId;

    @JsonProperty("price")
    private double price;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("title")
    private String title;

    @JsonProperty("updated_at")
    private String updatedAt;
}
