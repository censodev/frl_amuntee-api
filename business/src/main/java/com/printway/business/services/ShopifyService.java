package com.printway.business.services;

import com.printway.business.dto.shopify.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ShopifyService {
    List<ShopifyOrder> fetchListOrder(int storeId, LocalDateTime createdAtMin, int limit);
    List<ShopifyProduct> fetchProducts(ShopifyProductQueryParam params);
    ShopifyProduct saveProduct(ShopifyProduct shopifyProduct, int storeId);
    ShopifyProduct updateProduct(ShopifyProduct shopifyProduct, int storeId);
    ShopifyProductImage saveImage(String src, Long shopifyProductId, int storeId);
    void deleteImage(Long id, Long shopifyProductId, int storeId);
    ShopifyProductImage saveImageAsBase64(String src, Long shopifyProductId, int storeId);
    ShopifyProductVariant saveProductVariant(Long shopifyProductId, ShopifyProductVariant variant, int storeId);
    ShopifyProductVariant updateProductVariant(ShopifyProductVariant variant, int storeId);

}
