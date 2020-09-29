package com.amuntee.business.services;

import com.amuntee.business.dto.OrderShopify;
import com.amuntee.business.dto.OrderShopifyList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import com.sun.jersey.core.util.Base64;

@Service
@Slf4j
public class OrderShopifyServiceImpl implements OrderShopifyService {
    @Value("${shopify.host}")
    private String shopifyHost;

    @Value("${shopify.api-key}")
    private String shopifyApiKey;

    @Value("${shopify.password}")
    private String shopifyPassword;

    @Override
    public List<OrderShopify> getListOrder(String sinceId) {
        var http = new RestTemplateBuilder()
                .basicAuthentication(shopifyApiKey, shopifyPassword)
                .build();
        var url = new StringBuilder(shopifyHost + "orders.json")
                .append("?")
                .append("status=any")
                .append("&")
                .append("since_id=").append(sinceId);
        var orders = http.getForObject(url.toString(), OrderShopifyList.class);
        assert orders != null;
        return orders.getOrders();
    }
}
