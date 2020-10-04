package com.amuntee.business.services;

import com.amuntee.business.dto.OrderDTO;
import com.amuntee.business.dto.OrderStat;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface RevenueService {
    boolean syncShopifyOrders(int limit, boolean testMode);
    boolean syncShopifyPaymentTransactions(int limit, boolean testMode);
    Page<OrderDTO> listOrders(int page, int limit, String order, String orderBy);
    OrderDTO getOrderDetails(String orderCode);
    List<OrderStat> statRevenue(LocalDateTime from, LocalDateTime to);
}
