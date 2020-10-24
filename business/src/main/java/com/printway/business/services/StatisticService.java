package com.printway.business.services;

import com.printway.business.dto.statistic.*;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    List<SummaryStatistic> statForSummary(StatisticQueryParam params);
    List<ProductTypeStatistic> statForProductType(StatisticQueryParam params);
    List<ProductDesignStatistic> statForProductDesign(StatisticQueryParam params);
    List<SupplierStatistic> statForSupplier(StatisticQueryParam params);
    List<SellerStatistic> statForSeller(StatisticQueryParam params);
    List<DisputeStatistic> statForDispute(StatisticQueryParam params);
}
