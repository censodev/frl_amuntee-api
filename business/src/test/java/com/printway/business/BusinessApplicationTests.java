package com.printway.business;

import com.printway.business.services.RevenueService;
import com.printway.business.services.ShopifyService;
import com.printway.business.utils.SkuUtil;
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
	RevenueService revenueService;


	@Test
	void contextLoads() {
	}

	@Test
	void testFetchShopifyOrder() {
		var res = shopifyService.fetchListOrder("", 10);
		log.info(res.toString());
	}

	@Test
	void testFetchShopifyPaymentTransaction() {
		var res = shopifyService.fetchListPaymentTransaction("", 10);
		log.info(res.toString());
	}

	@Test
	void testSyncShopifyOrders() {
//		var rs = revenueService.syncShopifyOrders(10, true);
		var rs = revenueService.syncShopifyOrders(100, false);
		log.info(String.valueOf(rs));
	}

	@Test
	void testSyncShopifyPaymentTransactions() {
//		var rs = revenueService.syncShopifyPaymentTransactions(10, true);
		var rs = revenueService.syncShopifyPaymentTransactions(200, false);
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
		var rs1 = revenueService.listOrders(0, 10, "asc", "code");
		var rs2 = revenueService.getOrderDetails("2287528411197");
		log.info(rs1.toString());
		log.info(rs2.toString());
	}

	@Test
	void testStatistic() {
		var stat = revenueService.statForSummary(null, null);
		log.info("STAT ORDERS: " + stat.toString());
		var stat2 = revenueService.statForProductSku(null, null);
		log.info("STAT SKU: " + stat2.toString());
		stat2 = revenueService.statForProductCode(null, null);
		log.info("STAT PRD CODE: " + stat2.toString());
		stat2 = revenueService.statForProductDesign(null, null);
		log.info("STAT PRD DESIGN: " + stat2.toString());
		stat2 = revenueService.statForSupplier(null, null);
		log.info("STAT SUPPLIER: " + stat2.toString());
		stat2 = revenueService.statForSeller(null, null);
		log.info("STAT SELLER: " + stat2.toString());
	}

}
