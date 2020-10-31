package com.printway.business.requests;

import lombok.Data;

@Data
public class ConfigStoreRequest {
    private String facebookToken;
    private String telegramBotToken;
    private Integer status;
}
