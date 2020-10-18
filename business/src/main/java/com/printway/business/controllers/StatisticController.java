package com.printway.business.controllers;

import com.printway.business.dto.statistic.*;
import com.printway.business.services.StatisticService;
import com.printway.common.time.TimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("")
    public List<SummaryStatistic> statistic(@RequestParam long from,
                                            @RequestParam long to,
                                            @RequestParam(defaultValue = "") Integer storeId,
                                            @RequestParam(defaultValue = "") String sellerCode) {
        var fromTime = TimeParser.parseEpochMillisToLocalDateTime(from);
        var toTime = TimeParser.parseEpochMillisToLocalDateTime(to);
        return statisticService.statForSummary(new StatisticQueryParam(fromTime, toTime, storeId, sellerCode));
    }

    @GetMapping("product-type")
    public List<ProductTypeStatistic> statForProductType(@RequestParam long from,
                                                         @RequestParam long to,
                                                         @RequestParam(defaultValue = "") Integer storeId,
                                                         @RequestParam(defaultValue = "") String sellerCode) {
        var fromTime = TimeParser.parseEpochMillisToLocalDateTime(from);
        var toTime = TimeParser.parseEpochMillisToLocalDateTime(to);
        return statisticService.statForProductType(new StatisticQueryParam(fromTime, toTime, storeId, sellerCode));
    }

    @GetMapping("product-design")
    public List<ProductDesignStatistic> statForProductDesign(@RequestParam long from,
                                                             @RequestParam long to,
                                                             @RequestParam(defaultValue = "") Integer storeId,
                                                             @RequestParam(defaultValue = "") String sellerCode) {
        var fromTime = TimeParser.parseEpochMillisToLocalDateTime(from);
        var toTime = TimeParser.parseEpochMillisToLocalDateTime(to);
        return statisticService.statForProductDesign(new StatisticQueryParam(fromTime, toTime, storeId, sellerCode));
    }

    @GetMapping("seller")
    public List<SellerStatistic> statForSeller(@RequestParam long from,
                                               @RequestParam long to,
                                               @RequestParam(defaultValue = "") Integer storeId,
                                               @RequestParam(defaultValue = "") String sellerCode) {
        var fromTime = TimeParser.parseEpochMillisToLocalDateTime(from);
        var toTime = TimeParser.parseEpochMillisToLocalDateTime(to);
        return statisticService.statForSeller(new StatisticQueryParam(fromTime, toTime, storeId, sellerCode));
    }

    @GetMapping("supplier")
    public List<SupplierStatistic> statForSupplier(@RequestParam long from,
                                                   @RequestParam long to,
                                                   @RequestParam(defaultValue = "") Integer storeId,
                                                   @RequestParam(defaultValue = "") String sellerCode) {
        var fromTime = TimeParser.parseEpochMillisToLocalDateTime(from);
        var toTime = TimeParser.parseEpochMillisToLocalDateTime(to);
        return statisticService.statForSupplier(new StatisticQueryParam(fromTime, toTime, storeId, sellerCode));
    }
}
