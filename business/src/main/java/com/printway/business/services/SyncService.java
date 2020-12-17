package com.printway.business.services;

public interface SyncService {
    boolean syncShopifyOrders(int limit, boolean testMode);
    boolean syncFacebookInsights(boolean testMode) throws Exception;
    boolean syncShopifyProducts(int limit, boolean testMode);
}
