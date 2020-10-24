package com.printway.business.dto.statistic;

import lombok.Data;

@Data
public class DisputeStatistic {
    private Integer year;
    private Integer month;
    private Integer day;
    private Double dispute;
}
