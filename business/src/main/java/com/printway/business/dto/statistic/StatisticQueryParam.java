package com.printway.business.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticQueryParam {
    private LocalDateTime from;
    private LocalDateTime to;
    private Integer storeId;
    private String sellerCode;
}
