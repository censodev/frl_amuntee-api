package com.printway.business.services;

import com.printway.business.dto.OrderDTO;
import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.dto.statistic.RevenueSpecificStatistic;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    Page<OrderDTO> listOrders(int page, int limit, String order, String orderBy);
    OrderDTO getOrderDetails(String orderCode);
    List<SummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForProductSku(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForProductCode(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to);
}
