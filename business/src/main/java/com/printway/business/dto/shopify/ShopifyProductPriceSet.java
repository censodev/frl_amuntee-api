package com.printway.business.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShopifyProductPriceSet {
    @JsonProperty("shop_money")
    private ShopifyProductPrice shopMoney;

    @JsonProperty("presentment_money")
    private ShopifyProductPrice presentmentMoney;
}
