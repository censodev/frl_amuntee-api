package com.printway.business;

import com.printway.business.services.StatisticService;
import com.printway.business.services.ShopifyService;
import com.printway.business.services.SyncService;
import com.printway.business.utils.SkuUtil;
import com.printway.common.time.TimeParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class BusinessApplicationTests {
	@Autowired
	ShopifyService shopifyService;

	@Autowired
	StatisticService statisticService;

	@Autowired
	SyncService syncService;

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
	void testOrders() {
		var rs1 = statisticService.listOrders(0, 10, "asc", "code");
//		var rs2 = revenueService.getOrderDetails("2287528411197");
		log.info(rs1.toString());
//		log.info(rs2.toString());
	}

	@Test
	void testStatistic() {
		var from = TimeParser.parseEpochMillisToLocalDateTime(1600032915000L);
		var to = TimeParser.parseEpochMillisToLocalDateTime(1601920864000L);
		log.info("FROM: " + from.toString());
		log.info("TO: " + to.toString());
		var stat = statisticService.statForSummary(from, to);
		log.info("STAT SUMMARY: " + stat.toString());
		var stat2 = statisticService.statForProductSku(null, null);
		log.info("STAT SKU: " + stat2.toString());
		stat2 = statisticService.statForProductCode(null, null);
		log.info("STAT PRD CODE: " + stat2.toString());
		stat2 = statisticService.statForProductDesign(null, null);
		log.info("STAT PRD DESIGN: " + stat2.toString());
		stat2 = statisticService.statForSupplier(null, null);
		log.info("STAT SUPPLIER: " + stat2.toString());
		stat2 = statisticService.statForSeller(null, null);
		log.info("STAT SELLER: " + stat2.toString());
	}

}
