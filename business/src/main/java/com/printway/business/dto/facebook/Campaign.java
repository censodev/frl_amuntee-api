package com.printway.business.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Campaign {
    private String id;
    private String name;

    @JsonProperty("start_time")
    private String startTime;
}
