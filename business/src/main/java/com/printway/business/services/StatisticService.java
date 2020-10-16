package com.printway.business.services;

import com.printway.business.dto.statistic.*;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    List<SummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<ProductTypeStatistic> statForProductType(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<ProductDesignStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<SupplierStatistic> statForSupplier(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<SellerStatistic> statForSeller(LocalDateTime from, LocalDateTime to, Integer storeId);
}
