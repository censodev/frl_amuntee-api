package com.printway.business.services;

import com.printway.business.dto.facebook.AdAccounts;

public interface FacebookService {
    AdAccounts fetchAdAccounts() throws Exception;
}
