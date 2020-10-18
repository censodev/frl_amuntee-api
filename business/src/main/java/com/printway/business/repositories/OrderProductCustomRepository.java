package com.printway.business.repositories;

import com.printway.business.dto.statistic.*;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProductCustomRepository {
    List<ProductTypeStatistic> statForProductType(StatisticQueryParam params);
    List<ProductDesignStatistic> statForProductDesign(StatisticQueryParam params);
    List<SellerStatistic> statForSeller(StatisticQueryParam params);
    List<SupplierStatistic> statForSupplier(StatisticQueryParam params);
}
