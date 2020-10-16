package com.printway.business;

import com.printway.business.services.AccountService;
import com.printway.business.services.StatisticService;
import com.printway.business.services.ShopifyService;
import com.printway.business.services.SyncService;
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
		var rs = syncService.syncShopifyOrders(250, false);
		log.info(String.valueOf(rs));
	}

	@Test
	void testSku() {
		var skuUtil = new SkuUtil("ab - bc- defghjk");
		log.info(skuUtil.getSupplierCode());
		log.info(skuUtil.getProductCode());
		log.info(skuUtil.getSellerCode());
		log.info(skuUtil.getDesignCode());
	}

	@Test
	void testStatistic() {
		var from = TimeParser.parseEpochMillisToLocalDateTime(1600032915000L);
		var to = TimeParser.parseEpochMillisToLocalDateTime(1601920864000L);
		log.info("FROM: " + from.toString());
		log.info("TO: " + to.toString());
		var stat = statisticService.statForSummary(from, to, null);
		log.info("STAT SUMMARY: " + stat.toString());
		var stat2 = statisticService.statForProductType(from, to, null);
		log.info("STAT PRD TYPE: " + stat2.toString());
		var stat3 = statisticService.statForProductDesign(from, to, null);
		log.info("STAT PRD DESIGN: " + stat3.toString());
		var stat4 = statisticService.statForSupplier(from, to, null);
		log.info("STAT SUPPLIER: " + stat4.toString());
		var stat5 = statisticService.statForSeller(from, to, null);
		log.info("STAT SELLER: " + stat5.toString());
	}

	@Test
	public void testApiAuth() {
		var rs = accountService.listAccount();
		log.info(rs.toString());
	}
}
