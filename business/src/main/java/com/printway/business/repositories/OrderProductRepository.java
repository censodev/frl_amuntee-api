package com.printway.business.repositories;

import com.printway.business.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer>, OrderProductCustomRepository {
    List<OrderProduct> findByOrderCode(String orderCode);
}
