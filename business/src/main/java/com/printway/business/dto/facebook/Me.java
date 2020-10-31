package com.printway.business.dto.facebook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Me {
    @JsonProperty("adaccounts")
    AdAccounts adAccounts;
}
