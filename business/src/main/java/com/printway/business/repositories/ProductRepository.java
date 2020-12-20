package com.printway.business.repositories;

import com.printway.business.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByCreatedBy(Integer createdBy, Pageable pageable);
    Product findByShopifyId(Long shopifyId);
}
