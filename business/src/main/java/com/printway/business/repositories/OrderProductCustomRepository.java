package com.printway.business.repositories;

import com.printway.business.dto.statistic.RevenueSpecificStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProductCustomRepository {
    List<RevenueSpecificStatistic> statForSku(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForProductCode(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForDesignCode(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to);
    List<RevenueSpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to);
}
