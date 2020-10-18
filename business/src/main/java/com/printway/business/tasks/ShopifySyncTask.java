package com.printway.business.tasks;

import com.printway.business.services.SyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ShopifySyncTask {
    @Autowired
    private SyncService syncService;

    @Scheduled(cron = "0 0 0,12 ? * * *")
    public void syncOrders() {
        log.info("SYNC SHOPIFY ORDERS: STARTED");
        var rs = syncService.syncShopifyOrders(250, false);
        log.info("SYNC SHOPIFY ORDERS: DONE | status: " + rs);
    }
}
