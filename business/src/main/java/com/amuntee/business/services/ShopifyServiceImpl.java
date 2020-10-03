package com.amuntee.business.services;

import com.amuntee.business.dto.ShopifyOrder;
import com.amuntee.business.dto.ShopifyOrderList;
import com.amuntee.business.dto.ShopifyPaymentTransaction;
import com.amuntee.business.dto.ShopifyPaymentTransactionList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class ShopifyServiceImpl implements ShopifyService {
    @Value("${shopify.host}")
    private String shopifyHost;

    @Value("${shopify.api-key}")
    private String shopifyApiKey;

    @Value("${shopify.password}")
    private String shopifyPassword;

    private RestTemplate shopifyHttp;

    @PostConstruct
    private void postConstruct() {
        shopifyHttp = new RestTemplateBuilder()
                .basicAuthentication(shopifyApiKey, shopifyPassword)
                .build();
    }

    @Override
    public List<ShopifyOrder> fetchListOrder(String sinceId) {
        var url = new StringBuilder(shopifyHost + "orders.json")
                .append("?")
                .append("status=any")
                .append("&")
                .append("since_id=").append(sinceId);
        var orders = shopifyHttp.getForObject(url.toString(), ShopifyOrderList.class);
        assert orders != null;
        return orders.getOrders();
    }

    @Override
    public List<ShopifyPaymentTransaction> fetchListPaymentTransaction(String sinceId) {
        var url = new StringBuilder(shopifyHost + "shopify_payments/balance/transactions.json")
                .append("?")
                .append("since_id=").append(sinceId);
        var paymentTransactions = shopifyHttp
                .getForObject(url.toString(), ShopifyPaymentTransactionList.class);
        assert paymentTransactions != null;
        return paymentTransactions.getTransactions();
    }
}
