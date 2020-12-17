package com.printway.business.services;

import com.printway.business.dto.shopify.ShopifyOrder;
import com.printway.business.dto.shopify.ShopifyProduct;
import com.printway.business.dto.shopify.ShopifyProductQueryParam;

import java.time.LocalDateTime;
import java.util.List;

public interface ShopifyService {
    List<ShopifyOrder> fetchListOrder(int storeId, LocalDateTime createdAtMin, int limit);
    List<ShopifyProduct> fetchProducts(ShopifyProductQueryParam params);
    ShopifyProduct saveProduct(ShopifyProduct shopifyProduct, int storeId);
    ShopifyProduct updateProduct(ShopifyProduct shopifyProduct, int storeId);
}
