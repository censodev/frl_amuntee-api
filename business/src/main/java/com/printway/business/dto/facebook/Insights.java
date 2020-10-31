package com.printway.business.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Insights {
    private List<Insight> data;

    @Data
    public static class Insight {
        @JsonProperty("date_start")
        private String dateStart;

        @JsonProperty("date_stop")
        private String dateStop;

        private Double spend;
    }
}
