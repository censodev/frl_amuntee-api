package com.printway.business.services;

public interface SyncService {
    boolean syncShopifyOrders(int limit, boolean testMode);
    boolean syncShopifyPaymentTransactions(int limit, boolean testMode);
}
