package com.amuntee.business.services;

import com.amuntee.business.dto.ShopifyOrder;
import com.amuntee.business.dto.ShopifyPaymentTransaction;
import com.amuntee.business.models.Order;
import com.amuntee.business.models.OrderProduct;
import com.amuntee.business.models.PaymentTransaction;
import com.amuntee.business.repositories.OrderProductRepository;
import com.amuntee.business.repositories.OrderRepository;
import com.amuntee.business.repositories.PaymentTransactionRepository;
import com.amuntee.business.utils.SkuUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopifyService shopifyService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Override
    public boolean syncShopifyOrder(boolean testMode) {
        try {
            var lastOrder = orderRepository.findTopByOrderByIdDesc();
            var lastPaymentTransaction = paymentTransactionRepository.findTopByOrderByIdDesc();

            var sinceId = lastOrder != null ? lastOrder.getCode() : "";
            var shopifyOrders = shopifyService.fetchListOrder(sinceId);

            sinceId = lastPaymentTransaction != null ? lastPaymentTransaction.getCode() : "";
            var shopifyPaymentTransactions = shopifyService.fetchListPaymentTransaction(sinceId);

            saveOrders(shopifyOrders, testMode);
            saveOrderProducts(shopifyOrders, testMode);
            saveShopifyPaymentTransactions(shopifyPaymentTransactions, testMode);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }

    private void saveOrders(List<ShopifyOrder> shopifyOrders, boolean testMode) {
        if (shopifyOrders.isEmpty())
            return;
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
        log.info(orders.toString());

        if (!testMode) {
            orderRepository.saveAll(orders);
        }
    }

    private void saveOrderProducts(List<ShopifyOrder> shopifyOrders, boolean testMode) {
        if (shopifyOrders.isEmpty())
            return;
        var orderProducts = new ArrayList<OrderProduct>();
        shopifyOrders
                .forEach(shopifyOrder -> {
                    var products = shopifyOrder.getProducts()
                            .stream()
                            .map(prd -> {
                                var product = new OrderProduct();
                                product.setOrderCode(shopifyOrder.getCode());
                                product.setQuantity(prd.getQuantity());
                                product.setSku(prd.getSku());
                                product.setTitle(prd.getTitle());

                                try {
                                    var skuUtil = new SkuUtil(prd.getSku());
                                    product.setProductCode(skuUtil.getProductCode());
                                    product.setDesignCode(skuUtil.getDesignCode());
                                    product.setSellerCode(skuUtil.getSellerCode());
                                    product.setSupplierCode(skuUtil.getSupplierCode());
                                } catch (Exception ex) {
                                    log.warn("SKU format is invalid");
                                }

                                return product;
                            })
                            .collect(Collectors.toList());
                    orderProducts.addAll(products);
                });
        log.info(orderProducts.toString());
        if (!testMode) {
            orderProductRepository.saveAll(orderProducts);
        }
    }

    private void saveShopifyPaymentTransactions(List<ShopifyPaymentTransaction> shopifyPaymentTransactions, boolean testMode) {
        if (shopifyPaymentTransactions.isEmpty())
            return;
        var paymentTransactions = shopifyPaymentTransactions.stream()
                .map(shopifyPaymentTransaction -> new PaymentTransaction() {{
                    setCode(shopifyPaymentTransaction.getCode());
                    setType(shopifyPaymentTransaction.getType());
                    setSourceType(shopifyPaymentTransaction.getSourceType());
                    setAmount(shopifyPaymentTransaction.getAmount());
                    setFee(shopifyPaymentTransaction.getFee());
                    setNet(shopifyPaymentTransaction.getNet());
                    setOrderCode(shopifyPaymentTransaction.getOrderCode());
                }})
                .collect(Collectors.toList());
        log.info(paymentTransactions.toString());
        if (!testMode) {
            paymentTransactionRepository.saveAll(paymentTransactions);
        }
    }
}
