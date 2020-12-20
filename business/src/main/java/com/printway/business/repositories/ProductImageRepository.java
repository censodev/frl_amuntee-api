package com.printway.business.repositories;

import com.printway.business.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    ProductImage findByShopifyId(Long shopifyId);
}
