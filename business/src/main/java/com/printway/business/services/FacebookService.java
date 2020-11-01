package com.printway.business.services;

import com.printway.business.dto.facebook.AdAccounts;

import java.util.List;

public interface FacebookService {
    List<AdAccounts> fetchAdAccounts() throws Exception;
}
