package com.printway.business.services;

import com.printway.business.dto.shopify.ShopifyOrder;
import com.printway.business.dto.shopify.ShopifyOrderList;
import com.printway.business.dto.shopify.ShopifyPaymentTransaction;
import com.printway.business.dto.shopify.ShopifyPaymentTransactionList;
import com.printway.business.repositories.StoreRepository;
import com.printway.common.time.TimeParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopifyServiceImpl implements ShopifyService {
    @Autowired
    private StoreRepository storeRepository;

    private RestTemplate getShopifyHttpClient(String shopifyApiKey, String shopifyPassword) {
        return new RestTemplateBuilder()
                .basicAuthentication(shopifyApiKey, shopifyPassword)
                .build();
    }

    @Override
    public List<ShopifyOrder> fetchListOrder(int storeId, LocalDateTime createdAtMin, int limit) {
        createdAtMin = createdAtMin == null
                ? LocalDateTime.of(2020, 8, 1, 0, 0, 0)
                : createdAtMin;
        var from = TimeParser.parseLocalDateTimeToISOString(createdAtMin);
        log.info(from);
        var store = storeRepository.findById(storeId).orElse(null);
        assert store != null;
        var shopifyHttp = getShopifyHttpClient(store.getApiKey(), store.getPassword());
        var url = new StringBuilder(store.getHost() + "/admin/api/2020-10/orders.json")
                .append("?")
                .append("status=any")
                .append("&")
                .append("created_at_min=").append(from)
                .append("&")
                .append("limit=").append(limit);
        var orders = shopifyHttp.getForObject(url.toString(), ShopifyOrderList.class);
        assert orders != null;
        return orders.getOrders().stream()
                .sorted(Comparator.comparing(ShopifyOrder::getCode))
                .collect(Collectors.toList());
    }
}
