package com.printway.business.services;

import com.printway.business.dto.facebook.*;
import com.printway.business.repositories.ConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class FacebookServiceImpl implements FacebookService {
    @Autowired
    private ConfigRepository configRepository;

    private RestTemplate getFacebookTemplate(String token) {
        return new RestTemplateBuilder()
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    @Override
    public AdAccounts fetchAdAccounts() throws Exception {
        var fields = "adaccounts.limit(1000){id,name,account_status,age,amount_spent,spend_cap,balance,currency,is_prepay_account,business,funding_source_details,campaigns.limit(1000){name,id,insights{date_start,date_stop,spend}},adsets.limit(1000){id}}";
        var url = "https://graph.facebook.com/v8.0/me?fields={fields}";
        var config = configRepository.findFirstByStatus(1);
        if (config == null || config.getFacebookToken() == null)
            throw new Exception("Facebook token was not set");
        var data = getFacebookTemplate(config.getFacebookToken())
                .getForObject(url, Me.class, fields);
        assert data != null;
        return data.getAdAccounts();
    }
}
