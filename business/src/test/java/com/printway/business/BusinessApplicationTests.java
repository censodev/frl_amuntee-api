package com.printway.business;

import com.printway.business.services.*;
import com.printway.business.utils.SkuUtil;
import com.printway.common.time.TimeParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BusinessApplicationTests {
	@Autowired
	ShopifyService shopifyService;

	@Autowired
	StatisticService statisticService;

	@Autowired
	SyncService syncService;

	@Autowired
	AccountService accountService;

	@Autowired
	FacebookService facebookService;

	@Test
	void contextLoads() {
	}

	@Test
	void testFetchShopifyOrder() {
		var from = TimeParser.parseEpochMillisToLocalDateTime(1600032915000L);
//		var res = shopifyService.fetchListOrder(1, from, 10);
		var res = shopifyService.fetchListOrder(9, null, 250);
		log.info(res.toString());
	}

	@Test
	void testSyncShopifyOrders() {
//		var rs = syncService.syncShopifyOrders(250, true);
//		var rs = syncService.syncShopifyOrders(250, true);
//		log.info(String.valueOf(rs));
	}

	@Test
	void testSku() {
		var skuUtil = new SkuUtil("ab - bc- defghjk");
		log.info(skuUtil.getSupplierCode());
		log.info(skuUtil.getProductCode());
		log.info(skuUtil.getSellerCode());
		log.info(skuUtil.getDesignCode());
	}

//	@Test
//	public void testApiAuth() {
//		var rs = accountService.listAccount();
//		log.info(rs.toString());
//	}

    @Test
    void fbTest() {
//		log.info(facebookService.fetchAdAccounts().toString());
//		log.info(facebookService.fetchCampaigns("act_415270782763378").toString());
//		log.info(facebookService.fetchAds("23845555633560145").toString());
//		log.info(facebookService.fetchAdInsights("23845555633580145").toString());
//		syncService.syncFacebookInsights(false);
    }
}
