package com.printway.business.repositories;

import com.printway.business.models.ProductTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTemplateRepository extends JpaRepository<ProductTemplate, Integer> {
}
