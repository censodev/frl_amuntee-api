package com.printway.business.services;

import com.printway.business.dto.ShopifyOrder;
import com.printway.business.dto.ShopifyPaymentTransaction;

import java.util.List;

public interface ShopifyService {
    List<ShopifyOrder> fetchListOrder(String sinceId, int limit);
    List<ShopifyPaymentTransaction> fetchListPaymentTransaction(String sinceId, int limit);
}
