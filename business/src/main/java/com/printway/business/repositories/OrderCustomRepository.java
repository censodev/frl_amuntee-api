package com.printway.business.repositories;

import com.printway.business.dto.OrderStat;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderCustomRepository {
    List<OrderStat> statOrders(LocalDateTime from, LocalDateTime to);
}
