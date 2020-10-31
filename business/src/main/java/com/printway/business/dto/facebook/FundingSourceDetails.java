package com.printway.business.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FundingSourceDetails {
    private String id;

    @JsonProperty("display_string")
    private String displayString;

    private Integer type;
}
