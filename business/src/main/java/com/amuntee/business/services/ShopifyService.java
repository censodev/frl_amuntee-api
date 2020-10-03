package com.amuntee.business.services;

import com.amuntee.business.dto.ShopifyOrder;
import com.amuntee.business.dto.ShopifyPaymentTransaction;

import java.util.List;

public interface ShopifyService {
    List<ShopifyOrder> fetchListOrder(String sinceId);
    List<ShopifyPaymentTransaction> fetchListPaymentTransaction(String sinceId);
}
