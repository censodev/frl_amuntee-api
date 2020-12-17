package com.printway.business.services;

import com.printway.business.dto.shopify.*;
import com.printway.business.repositories.StoreRepository;
import com.printway.common.time.TimeParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
        String url = store.getHost() + "/admin/api/2020-10/orders.json" +
                "?" +
                "status=any" +
                "&" +
                "created_at_min=" + from +
                "&" +
                "limit=" + limit;
        var orders = shopifyHttp.getForObject(url, ShopifyOrders.class);
        assert orders != null;
        return orders.getOrders().stream()
                .sorted(Comparator.comparing(ShopifyOrder::getCode))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopifyProduct> fetchProducts(ShopifyProductQueryParam params) {
        var store = storeRepository.findById(params.getStoreId()).orElse(null);
        assert store != null;
        var shopifyHttp = getShopifyHttpClient(store.getApiKey(), store.getPassword());
        String url = store.getHost() + "/admin/api/2020-10/products.json" +
                "?" +
                "limit=" + params.getLimit();
        if (params.getStatus() != null) {
            url += "&status=" + params.getStatus();
        }
        var products = shopifyHttp.getForObject(url, ShopifyProducts.class);
        assert products != null;
        return products.getProducts();
    }

    @Override
    public ShopifyProduct saveProduct(ShopifyProduct shopifyProduct, int storeId) {
        var store = storeRepository.findById(storeId).orElse(null);
        assert store != null;
        var shopifyHttp = getShopifyHttpClient(store.getApiKey(), store.getPassword());
        String url = store.getHost() + "/admin/api/2020-10/products.json";
        var body = HttpProduct.builder().product(shopifyProduct).build();
        var res = shopifyHttp.postForObject(url, body, HttpProduct.class);
        assert res != null;
        return res.getProduct();
    }

    @Override
    public ShopifyProduct updateProduct(ShopifyProduct shopifyProduct, int storeId) {
        var store = storeRepository.findById(storeId).orElse(null);
        assert store != null;
        var shopifyHttp = getShopifyHttpClient(store.getApiKey(), store.getPassword());
        String url = store.getHost() + "/admin/api/2020-10/products/" + shopifyProduct.getId() + ".json";
        var requestEntity = new HttpEntity<HttpProduct>(HttpProduct.builder().product(shopifyProduct).build());
        var res = shopifyHttp.exchange(url, HttpMethod.PUT, requestEntity, HttpProduct.class);
        return Objects.requireNonNull(res.getBody()).getProduct();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class HttpProduct {
        private ShopifyProduct product;
    }
}
