package com.printway.business.services;

import com.printway.business.dto.shopify.ShopifyOrder;
import com.printway.business.models.Order;
import com.printway.business.models.OrderProduct;
import com.printway.business.repositories.OrderProductRepository;
import com.printway.business.repositories.OrderRepository;
import com.printway.business.repositories.StoreRepository;
import com.printway.business.utils.SkuUtil;
import com.printway.common.time.TimeParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SyncServiceImpl implements SyncService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopifyService shopifyService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public boolean syncShopifyOrders(int limit, boolean testMode) {
        try {
            var stores = storeRepository.findAll();
            for (var store : stores) {
                var shopifyOrders = shopifyService
                        .fetchListOrder(store.getId(), store.getSyncTime(), limit);
                saveOrders(store.getId(), shopifyOrders, testMode);
                saveOrderProducts(shopifyOrders, testMode);

                if (!testMode) {
                    var syncTime = TimeParser
                            .parseZonedDateTimeToLocalDateTime(shopifyOrders.get(shopifyOrders.size() - 1).getCreatedAt());
                    store.setSyncTime(syncTime);
                }

            }
            storeRepository.saveAll(stores);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveOrders(int storeId, List<ShopifyOrder> shopifyOrders, boolean testMode) {
        if (shopifyOrders.isEmpty())
            return;
        var orders = shopifyOrders.stream()
                .map(shopifyOrder -> {
                    var order = new Order();
                    order.setCode(shopifyOrder.getCode());
                    order.setName(shopifyOrder.getName());
                    order.setPaygateName(shopifyOrder.getPaygateName());
                    order.setRevenue(shopifyOrder.getSubTotalPrice());
                    order.setFinancialStatus(shopifyOrder.getFinancialStatus());
                    order.setFulfillmentStatus(shopifyOrder.getFulfillmentStatus());
                    order.setCreatedAt(TimeParser.parseZonedDateTimeToLocalDateTime(shopifyOrder.getCreatedAt()));
                    order.setUpdatedAt(TimeParser.parseZonedDateTimeToLocalDateTime(shopifyOrder.getUpdatedAt()));
                    order.setClosedAt(TimeParser.parseZonedDateTimeToLocalDateTime(shopifyOrder.getClosedAt()));
                    order.setStoreId(storeId);
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
                                var sku = prd.getSku() == null
                                        ? null
                                        : prd.getSku().replace(" ", "");
                                product.setSku(sku);
                                product.setTitle(prd.getTitle());
                                product.setPrice(prd.getPrice());

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
}
