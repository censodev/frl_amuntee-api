package com.printway.business.services;

import com.printway.business.dto.OrderDTO;
import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.dto.statistic.SpecificStatistic;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    Page<OrderDTO> listOrders(int page, int limit, String order, String orderBy);
    OrderDTO getOrderDetails(String orderCode);
    List<SummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForProductSku(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForProductCode(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to);
}
