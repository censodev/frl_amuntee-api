package com.printway.business.repositories;

import com.printway.business.dto.statistic.DisputeStatistic;
import com.printway.business.dto.statistic.StatisticQueryParam;

import java.util.List;

public interface DisputeCustomRepository {
    List<DisputeStatistic> statForDispute(StatisticQueryParam params);
}
