package com.amuntee.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderShopify {
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
    private List<ProductShopify> products;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("closed_at")
    private String closedAt;
}
