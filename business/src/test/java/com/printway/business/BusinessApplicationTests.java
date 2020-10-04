package com.printway.business;

import com.printway.business.repositories.OrderRepository;
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

	@Autowired
	OrderRepository orderRepository;

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
		var rs = revenueService.syncShopifyOrders(200, false);
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
		var skuUtil = new SkuUtil("ab-bc-defghjk");
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
	void testOrderStat() {
		var stat = orderRepository.statOrders(null, null);
		log.info(stat.toString());
	}

}
