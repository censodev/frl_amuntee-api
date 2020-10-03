package com.amuntee.business;

import com.amuntee.business.services.OrderService;
import com.amuntee.business.services.ShopifyService;
import com.amuntee.business.utils.SkuUtil;
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
	OrderService orderService;

	@Test
	void contextLoads() {
	}

	@Test
	void testFetchShopifyOrder() {
		var res = shopifyService.fetchListOrder("");
		log.info(res.toString());
	}

	@Test
	void testFetchShopifyPaymentTransaction() {
		var res = shopifyService.fetchListPaymentTransaction("");
		log.info(res.toString());
	}

	@Test
	void testSyncShopifyOrders() {
		orderService.syncShopifyOrder(true);
	}

	@Test
	void testSku() {
		var skuUtil = new SkuUtil("ab-bc-defghjk");
		log.info(skuUtil.getSupplierCode());
		log.info(skuUtil.getProductCode());
		log.info(skuUtil.getSellerCode());
		log.info(skuUtil.getDesignCode());
	}

}
