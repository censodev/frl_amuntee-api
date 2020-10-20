package com.printway.business.services;

import com.printway.business.dto.facebook.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class FacebookServiceImpl implements FacebookService {
    @Value("${spring.social.facebook.accessToken}")
    private String accessToken;

    private String api = "https://graph.facebook.com/v8.0";

    private RestTemplate getFacebookTemplate() {
        return new RestTemplateBuilder()
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
    }

    @Override
    public List<AdAccount> fetchAdAccounts() {
        var url = api + "/me?fields=adaccounts";
        var data = getFacebookTemplate().getForObject(url, AdAccountSet.class);
        assert data != null;
        return data.getAdaccounts().getData();
    }

    @Override
    public List<Campaign> fetchCampaigns(String accountId) {
        var url = api + "/" + accountId + "/campaigns?fields=name,start_time";
        var data = getFacebookTemplate().getForObject(url, CampaignSet.class);
        assert data != null;
        return data.getData();
    }

    @Override
    public List<Ad> fetchAds(String campaignId) {
        var url = api + "/" + campaignId + "/adsets";
        var data = getFacebookTemplate().getForObject(url, AdSet.class);
        assert data != null;
        return data.getData();
    }

    @Override
    public List<AdInsights> fetchAdInsights(String adId) {
        var url = api + "/" + adId + "/insights";
        var data = getFacebookTemplate().getForObject(url, AdInsightsSet.class);
        assert data != null;
        return data.getData();
    }
}
