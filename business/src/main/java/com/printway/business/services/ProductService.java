package com.printway.business.services;

import com.printway.business.dto.ImageUpload;
import com.printway.business.dto.shopify.ShopifyProduct;
import com.printway.business.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByCreatedBy(Integer createdBy, Pageable pageable);
    Product find(int id);
    Product saveAndSync(Product product);
    Product updateAndSync(int id, Product product);
    ProductImage saveAndSyncImage(ImageUpload imageUpload);
    ProductImage saveAndSyncImageBase64(ImageUpload imageUpload);
    void deleteAndSyncImage(Long id, Long shopifyProductId, int storeId);
    Product convert(Integer id,
                    ShopifyProduct shopifyProduct,
                    Store store,
                    Integer createdBy,
                    ProductTemplate productTemplate,
                    List<ProductVariant> variants);
    ShopifyProduct convert(Product product);
    ProductVariant findVariant(Integer id);
    ProductVariant updateVariant(Integer id, ProductVariant variant, int storeId);
    ProductVariant createVariant(ProductVariant variant, int storeId);
}
