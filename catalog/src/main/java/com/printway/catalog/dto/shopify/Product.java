package com.printway.catalog.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Product {
    @JsonProperty("id")
    private int id;

    @JsonProperty("body_html")
    private String bodyHtml;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("images")
    private List<ProductImage> images;

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("published_at")
    private String publishedAt;

    @JsonProperty("published_scope")
    private String publishedScope;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tags")
    private String tags;

    @JsonProperty("title")
    private String title;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("variants")
    private List<ProductVariant> variants;

    @JsonProperty("vendor")
    private String vendor;
}
