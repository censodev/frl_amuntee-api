package com.amuntee.business.services;

import com.amuntee.business.dto.OrderShopify;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface OrderShopifyService {
    List<OrderShopify> getListOrder(String sinceId);
}
