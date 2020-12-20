package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyProduct {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("body_html")
    private String bodyHtml;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("images")
    private List<ShopifyProductImage> images;

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
    private List<ShopifyProductVariant> variants;

    @JsonProperty("vendor")
    private String vendor;
}
