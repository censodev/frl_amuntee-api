package com.printway.business.controllers;

import com.printway.business.dto.OrderDTO;
import com.printway.business.dto.statistic.RevenueSummaryStatistic;
import com.printway.business.dto.statistic.RevenueSpecificStatistic;
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
    public List<RevenueSummaryStatistic> statistic() {
        return revenueService.statForSummary(null, null);
    }

    @GetMapping("statistic/product-sku")
    public List<RevenueSpecificStatistic> statForProductSku() {
        return revenueService.statForProductSku(null, null);
    }

    @GetMapping("statistic/product-code")
    public List<RevenueSpecificStatistic> statForProductCode() {
        return revenueService.statForProductCode(null, null);
    }

    @GetMapping("statistic/product-design")
    public List<RevenueSpecificStatistic> statForProductDesign() {
        return revenueService.statForProductDesign(null, null);
    }

    @GetMapping("statistic/seller")
    public List<RevenueSpecificStatistic> statForSeller() {
        return revenueService.statForSeller(null, null);
    }

    @GetMapping("statistic/supplier")
    public List<RevenueSpecificStatistic> statForSupplier() {
        return revenueService.statForSupplier(null, null);
    }
}
