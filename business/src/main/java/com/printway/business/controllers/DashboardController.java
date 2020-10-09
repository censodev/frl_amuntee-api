package com.printway.business.controllers;

import com.printway.business.dto.OrderDTO;
import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.dto.statistic.RevenueSpecificStatistic;
import com.printway.business.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/revenue")
public class DashboardController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("order")
    public Page<OrderDTO> listOrder(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int limit,
                                    @RequestParam(defaultValue = "code") String orderBy,
                                    @RequestParam(defaultValue = "desc") String order) {
        return statisticService.listOrders(page, limit, order, orderBy);
    }

    @GetMapping("order/{code}")
    public OrderDTO findOrder(@PathVariable String code) {
        return statisticService.getOrderDetails(code);
    }

    @GetMapping("statistic")
    public List<SummaryStatistic> statistic() {
        return statisticService.statForSummary(null, null);
    }

    @GetMapping("statistic/product-sku")
    public List<RevenueSpecificStatistic> statForProductSku() {
        return statisticService.statForProductSku(null, null);
    }

    @GetMapping("statistic/product-code")
    public List<RevenueSpecificStatistic> statForProductCode() {
        return statisticService.statForProductCode(null, null);
    }

    @GetMapping("statistic/product-design")
    public List<RevenueSpecificStatistic> statForProductDesign() {
        return statisticService.statForProductDesign(null, null);
    }

    @GetMapping("statistic/seller")
    public List<RevenueSpecificStatistic> statForSeller() {
        return statisticService.statForSeller(null, null);
    }

    @GetMapping("statistic/supplier")
    public List<RevenueSpecificStatistic> statForSupplier() {
        return statisticService.statForSupplier(null, null);
    }
}
