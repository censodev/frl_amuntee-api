package com.printway.business.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdInsights {
    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_stop")
    private String dateStop;

    private Double spend;
}
