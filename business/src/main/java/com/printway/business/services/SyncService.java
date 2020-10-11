package com.printway.business.services;

public interface SyncService {
    boolean syncShopifyOrders(int limit, boolean testMode);
}
