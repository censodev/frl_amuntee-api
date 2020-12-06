package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ShopifyProduct {
    @JsonProperty("id")
    private int id;

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

    public static class ShopifyProductPublishedScope {
        public static final String WEB = "web";
        public static final String GLOBAL = "global";
    }

    public static class ShopifyProductStatus {
        public static final String ACTIVE = "active";
        public static final String ARCHIVED = "archived";
        public static final String DRAFT = "draft";
    }
}
