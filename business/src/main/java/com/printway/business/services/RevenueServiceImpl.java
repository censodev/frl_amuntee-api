package com.printway.business.services;

import com.amuntee.business.dto.*;
import com.printway.business.dto.OrderDTO;
import com.printway.business.dto.OrderStat;
import com.printway.business.dto.ShopifyOrder;
import com.printway.business.dto.ShopifyPaymentTransaction;
import com.printway.business.models.Order;
import com.printway.business.models.OrderProduct;
import com.printway.business.models.PaymentTransaction;
import com.printway.business.repositories.OrderProductRepository;
import com.printway.business.repositories.OrderRepository;
import com.printway.business.repositories.PaymentTransactionRepository;
import com.printway.business.utils.SkuUtil;
import com.printway.common.json.RestResponsePage;
import com.printway.common.time.TimeParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopifyService shopifyService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean syncShopifyOrders(int limit, boolean testMode) {
        try {
            var lastOrder = orderRepository.findTopByOrderByCodeDesc();
            var sinceId = lastOrder != null ? lastOrder.getCode() : "";
            var shopifyOrders = shopifyService.fetchListOrder(sinceId, limit);
            saveOrders(shopifyOrders, testMode);
            saveOrderProducts(shopifyOrders, testMode);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean syncShopifyPaymentTransactions(int limit, boolean testMode) {
        try {
            var lastPaymentTransaction = paymentTransactionRepository.findTopByOrderByCodeDesc();
            var sinceId = lastPaymentTransaction != null ? lastPaymentTransaction.getCode() : "";
            var shopifyPaymentTransactions = shopifyService
                    .fetchListPaymentTransaction(sinceId, limit);
            savePaymentTransactions(shopifyPaymentTransactions, testMode);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Page<OrderDTO> listOrders(int page, int limit, String order, String orderBy) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        var orders = orderRepository.findAll(PageRequest.of(page, limit, sort));
        var typeRef = new TypeReference<RestResponsePage<OrderDTO>>() {};
        return objectMapper.convertValue(orders, typeRef);
    }

    @Override
    public OrderDTO getOrderDetails(String orderCode) {
        var products = orderProductRepository.findByOrderCode(orderCode);
        var paymentTransactions = paymentTransactionRepository
                .findByOrderCode(orderCode);
        var order = orderRepository.findByCode(orderCode);
        var orderDto = objectMapper.convertValue(order, OrderDTO.class);
        orderDto.setProducts(products);
        orderDto.setPaymentTransactions(paymentTransactions);
        return orderDto;
    }

    @Override
    public List<OrderStat> statRevenue(LocalDateTime from, LocalDateTime to) {
        return orderRepository.statOrders(from, to);
    }

    private void saveOrders(List<ShopifyOrder> shopifyOrders, boolean testMode) {
        if (shopifyOrders.isEmpty())
            return;
        var orders = shopifyOrders.stream()
                .map(shopifyOrder -> {
                    var order = new Order();
                    order.setCode(shopifyOrder.getCode());
                    order.setName(shopifyOrder.getName());
                    order.setPaygateName(shopifyOrder.getPaygateName());
                    order.setSubTotalPrice(shopifyOrder.getSubTotalPrice());
                    order.setTotalPrice(shopifyOrder.getTotalPrice());
                    order.setFinancialStatus(shopifyOrder.getFinancialStatus());
                    order.setFulfillmentStatus(shopifyOrder.getFulfillmentStatus());
                    order.setCreatedAt(TimeParser.parse(shopifyOrder.getCreatedAt()));
                    order.setUpdatedAt(TimeParser.parse(shopifyOrder.getUpdatedAt()));
                    order.setClosedAt(TimeParser.parse(shopifyOrder.getClosedAt()));
                    return order;
                })
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

    private void savePaymentTransactions(List<ShopifyPaymentTransaction> shopifyPaymentTransactions, boolean testMode) {
        if (shopifyPaymentTransactions.isEmpty())
            return;
        var paymentTransactions = shopifyPaymentTransactions.stream()
                .map(shopifyPaymentTransaction -> {
                    var trans = new PaymentTransaction();
                    trans.setCode(shopifyPaymentTransaction.getCode());
                    trans.setType(shopifyPaymentTransaction.getType());
                    trans.setSourceType(shopifyPaymentTransaction.getSourceType());
                    trans.setAmount(shopifyPaymentTransaction.getAmount());
                    trans.setFee(shopifyPaymentTransaction.getFee());
                    trans.setNet(shopifyPaymentTransaction.getNet());
                    trans.setOrderCode(shopifyPaymentTransaction.getOrderCode());
                    return trans;
                })
                .collect(Collectors.toList());
        log.info(paymentTransactions.toString());
        if (!testMode) {
            paymentTransactionRepository.saveAll(paymentTransactions);
        }
    }
}
