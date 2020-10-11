package com.printway.business.repositories;

import com.printway.business.dto.statistic.SpecificStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProductCustomRepository {
    List<SpecificStatistic> statForSku(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForProductCode(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForDesignCode(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to);
    List<SpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to);
}
