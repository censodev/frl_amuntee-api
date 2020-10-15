package com.printway.business.repositories;

import com.printway.business.dto.statistic.ProductTypeStatistic;
import com.printway.business.dto.statistic.SellerStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProductCustomRepository {
    List<ProductTypeStatistic> statForProductType(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<ProductTypeStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<SellerStatistic> statForSeller(LocalDateTime from, LocalDateTime to, Integer storeId);
    List<ProductTypeStatistic> statForSupplier(LocalDateTime from, LocalDateTime to, Integer storeId);
}
