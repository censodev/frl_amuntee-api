package com.amuntee.business.services;

import com.amuntee.business.dto.OrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    boolean syncShopifyOrders(boolean testMode);
    List<OrderDTO> listOrders(int page, int limit, String order, String orderBy);
    OrderDTO getOrderDetails(String orderCode);
}
