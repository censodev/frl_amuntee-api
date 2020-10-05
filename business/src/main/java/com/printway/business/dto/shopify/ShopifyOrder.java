package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyOrder {
    @JsonProperty("id")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("subtotal_price")
    private Double subTotalPrice;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("financial_status")
    private String financialStatus;

    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;

    @JsonProperty("gateway")
    private String paygateName;

    @JsonProperty("line_items")
    private List<ShopifyOrderProduct> products;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("closed_at")
    private String closedAt;
}
