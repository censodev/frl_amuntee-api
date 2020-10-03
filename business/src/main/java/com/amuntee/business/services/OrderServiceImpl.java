package com.amuntee.business.services;

import com.amuntee.business.dto.OrderDTO;
import com.amuntee.business.dto.ShopifyOrder;
import com.amuntee.business.dto.ShopifyPaymentTransaction;
import com.amuntee.business.models.Order;
import com.amuntee.business.models.OrderProduct;
import com.amuntee.business.models.PaymentTransaction;
import com.amuntee.business.repositories.OrderProductRepository;
import com.amuntee.business.repositories.OrderRepository;
import com.amuntee.business.repositories.PaymentTransactionRepository;
import com.amuntee.business.utils.SkuUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean syncShopifyOrders(boolean testMode) {
        try {
            var lastOrder = orderRepository.findTopByOrderByCodeDesc();
            var lastPaymentTransaction = paymentTransactionRepository.findTopByOrderByCodeDesc();

            var sinceId = lastOrder != null ? lastOrder.getCode() : "";
            var shopifyOrders = shopifyService.fetchListOrder(sinceId);

            sinceId = lastPaymentTransaction != null ? lastPaymentTransaction.getCode() : "";
            var shopifyPaymentTransactions = shopifyService.fetchListPaymentTransaction(sinceId);

            saveOrders(shopifyOrders, testMode);
            saveOrderProducts(shopifyOrders, testMode);
            savePaymentTransactions(shopifyPaymentTransactions, testMode);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<OrderDTO> listOrders(int page, int limit, String order, String orderBy) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        var orders = orderRepository.findAll(PageRequest.of(page, limit, sort)).getContent();
        var type = new TypeReference<List<OrderDTO>>() {};

        return objectMapper.convertValue(orders, type).stream()
                .peek(orderDTO -> {
                    var products = orderProductRepository.findByOrderCode(orderDTO.getCode());
                    var paymentTransactions = paymentTransactionRepository
                            .findByOrderCode(orderDTO.getCode());
                    orderDTO.setProducts(products);
                    orderDTO.setPaymentTransactions(paymentTransactions);
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderDetails(String orderCode) {
        return null;
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
                    order.setCreatedAt(shopifyOrder.getCreatedAt());
                    order.setUpdatedAt(shopifyOrder.getUpdatedAt());
                    order.setClosedAt(shopifyOrder.getClosedAt());
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
