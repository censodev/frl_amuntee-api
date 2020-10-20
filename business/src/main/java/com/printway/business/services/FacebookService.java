package com.printway.business.services;

import com.printway.business.dto.facebook.Ad;
import com.printway.business.dto.facebook.AdAccount;
import com.printway.business.dto.facebook.AdInsights;
import com.printway.business.dto.facebook.Campaign;

import java.util.List;

public interface FacebookService {
    List<AdAccount> fetchAdAccounts();
    List<Campaign> fetchCampaigns(String accountId);
    List<Ad> fetchAds(String campaignId);
    List<AdInsights> fetchAdInsights(String adId);
}
