package com.printway.business.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigStoreRequest {
    private String facebookTokenTitle;
    private String facebookToken;
    private String telegramBotToken;
    private Integer status;
}
