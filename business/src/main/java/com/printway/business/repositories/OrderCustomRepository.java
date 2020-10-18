package com.printway.business.repositories;

import com.printway.business.dto.statistic.StatisticQueryParam;
import com.printway.business.dto.statistic.SummaryStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderCustomRepository {
    List<SummaryStatistic> statForSummary(StatisticQueryParam params);
}
