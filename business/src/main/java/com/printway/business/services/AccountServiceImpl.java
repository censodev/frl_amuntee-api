package com.printway.business.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.common.auth.User;
import com.printway.common.json.RestResponsePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService {
    @Value("${printway.auth.domain}")
    private String AUTH_DOMAIN;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Page<User> listAccount() {
        var http = new RestTemplate();
        var url = AUTH_DOMAIN + "/api/user?limit=10000000";
        var typeRef = new TypeReference<RestResponsePage<User>>() {};
        var rs = http.getForObject(url, Object.class);
        return objectMapper.convertValue(rs, typeRef);
    }
}
