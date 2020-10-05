package com.printway.business.repositories;

import com.printway.business.dto.statistic.RevenueSummaryStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderCustomRepository {
    List<RevenueSummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to);
}
