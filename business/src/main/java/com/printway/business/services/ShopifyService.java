package com.printway.business.services;

import com.printway.business.dto.shopify.ShopifyOrder;
import com.printway.business.dto.shopify.ShopifyPaymentTransaction;

import java.time.LocalDateTime;
import java.util.List;

public interface ShopifyService {
    List<ShopifyOrder> fetchListOrder(int storeId, LocalDateTime createdAtMin, int limit);
}
