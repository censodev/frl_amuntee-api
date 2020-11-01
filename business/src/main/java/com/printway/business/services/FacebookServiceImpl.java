package com.printway.business.services;

import com.printway.business.dto.facebook.*;
import com.printway.business.models.Config;
import com.printway.business.repositories.ConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<AdAccounts> fetchAdAccounts() throws Exception {
        var fields = "adaccounts.limit(1000){id,name,account_status,age,amount_spent,spend_cap,balance,currency,is_prepay_account,business,funding_source_details,campaigns.limit(1000){name,id,insights{date_start,date_stop,spend}},adsets.limit(1000){id}}";
        var url = "https://graph.facebook.com/v8.0/me?fields={fields}";
        var tokens = configRepository.findAllByStatus(1)
                .stream()
                .filter(i -> !i.getFacebookToken().equals("") && i.getFacebookToken() != null)
                .map(Config::getFacebookToken)
                .collect(Collectors.toList());
        if (tokens.size() == 0)
            throw new Exception("Facebook token was not set");
        return tokens.stream().map(token -> {
            var data = getFacebookTemplate(token)
                    .getForObject(url, Me.class, fields);
            assert data != null;
            return data.getAdAccounts();
        }).collect(Collectors.toList());
    }
}
