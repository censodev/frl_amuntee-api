package com.amuntee.business.repositories;

import com.amuntee.business.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findTopByOrderByIdDesc();
}
