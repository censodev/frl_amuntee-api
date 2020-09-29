package com.amuntee.business;

import com.amuntee.business.services.OrderService;
import com.amuntee.business.services.OrderShopifyService;
import com.amuntee.business.utils.SkuUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BusinessApplicationTests {
	@Autowired
	OrderShopifyService orderShopifyService;

	@Autowired
	OrderService orderService;

	@Test
	void contextLoads() {
	}

	@Test
	void testFetchShopifyAPI() {
		var res = orderShopifyService.getListOrder(null);
		log.info(res.toString());
	}

	@Test
	void testSyncShopifyOrders() {
		var rs = orderService.syncShopifyOrder();
		log.info("" + rs);
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
