package com.amuntee.business.services;

import com.amuntee.business.dto.OrderShopify;
import com.amuntee.business.models.Order;
import com.amuntee.business.models.OrderProduct;
import com.amuntee.business.repositories.OrderProductRepository;
import com.amuntee.business.repositories.OrderRepository;
import com.amuntee.business.utils.SkuUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderShopifyService orderShopifyService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean syncShopifyOrder() {
        try {
            var lastOrder = orderRepository.findTopByOrderByIdDesc();
            var sinceId = lastOrder != null ? lastOrder.getCode() : "";
            var shopifyOrders = orderShopifyService.getListOrder(sinceId);
            var orders = shopifyOrders.stream()
                    .map(shopifyOrder -> new Order() {{
                        setCode(shopifyOrder.getCode());
                        setName(shopifyOrder.getName());
                        setPaygateName(shopifyOrder.getPaygateName());
                        setSubTotalPrice(shopifyOrder.getSubTotalPrice());
                        setTotalPrice(shopifyOrder.getTotalPrice());
                        setFinancialStatus(shopifyOrder.getFinancialStatus());
                        setFulfillmentStatus(shopifyOrder.getFulfillmentStatus());
                        setCreatedAt(shopifyOrder.getCreatedAt());
                        setUpdatedAt(shopifyOrder.getUpdatedAt());
                        setClosedAt(shopifyOrder.getClosedAt());
                    }})
                    .collect(Collectors.toList());
            var orderProducts = new ArrayList<OrderProduct>();
            shopifyOrders
                    .forEach(shopifyOrder -> {
                        var products = shopifyOrder.getProducts()
                                .stream()
                                .map(prd -> {
                                    var product = new OrderProduct();
                                    var skuUtil = new SkuUtil(prd.getSku());
                                    product.setOrderCode(shopifyOrder.getCode());
                                    product.setQuantity(prd.getQuantity());
                                    product.setProductCode(skuUtil.getProductCode());
                                    product.setDesignCode(skuUtil.getDesignCode());
                                    product.setSellerCode(skuUtil.getSellerCode());
                                    product.setSupplierCode(skuUtil.getSupplierCode());
                                    return product;
                                })
                                .collect(Collectors.toList());
                        orderProducts.addAll(products);
                    });
            log.info(orders.toString());
            log.info(orderProducts.toString());
//            orderRepository.saveAll(orders);
//            orderProductRepository.saveAll(orderProducts);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }
}
