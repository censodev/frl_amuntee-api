package com.printway.business.tasks;

import com.printway.business.services.SyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FacebookSyncTask {
    @Autowired
    private SyncService syncService;

    @Scheduled(cron = "0 0 1 ? * *")
    public void syncMarketingInsights() {
        log.info("SYNC FACEBOOK MARKETING INSIGHTS: STARTED");
        var rs = syncService.syncFacebookInsights(false);
        log.info("SYNC FACEBOOK MARKETING INSIGHTS: DONE | status: " + rs);
    }
}
