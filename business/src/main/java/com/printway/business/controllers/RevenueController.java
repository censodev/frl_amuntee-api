package com.printway.business.controllers;

import com.printway.business.dto.OrderDTO;
import com.printway.business.dto.OrderStat;
import com.printway.business.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/revenue")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @GetMapping("order")
    public Page<OrderDTO> listOrder(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int limit,
                                    @RequestParam(defaultValue = "code") String orderBy,
                                    @RequestParam(defaultValue = "desc") String order) {
        return revenueService.listOrders(page, limit, order, orderBy);
    }

    @GetMapping("order/{code}")
    public OrderDTO findOrder(@PathVariable String code) {
        return revenueService.getOrderDetails(code);
    }

    @GetMapping("statistic")
    public List<OrderStat> statistic() {
        return revenueService.statRevenue(null, null);
    }
}
