package com.printway.business.repositories;

import com.printway.business.dto.statistic.SpecificStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProductCustomRepository {
    List<SpecificStatistic> statForProductType(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<SpecificStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<SpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<SpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to, Integer storeId);
}
