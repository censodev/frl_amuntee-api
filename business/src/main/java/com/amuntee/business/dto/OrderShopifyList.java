package com.amuntee.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OrderShopifyList {
    @JsonProperty("orders")
    List<OrderShopify> orders;
}
