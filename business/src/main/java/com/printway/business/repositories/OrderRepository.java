package com.printway.business.repositories;

import com.printway.business.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, OrderCustomRepository {
    Order findTopByOrderByCodeDesc();
    Order findByCode(String code);
    List<Order> findByClosedAtBetween(LocalDateTime from, LocalDateTime to);
}
