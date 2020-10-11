package com.printway.business.controllers;

import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.dto.statistic.SpecificStatistic;
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
                                            @RequestParam long to) {
        var fromTime = TimeParser.parseEpochMillisToLocalDateTime(from);
        var toTime = TimeParser.parseEpochMillisToLocalDateTime(to);
        return statisticService.statForSummary(fromTime, toTime);
    }

    @GetMapping("product-sku")
    public List<SpecificStatistic> statForProductSku() {
        return statisticService.statForProductSku(null, null);
    }

    @GetMapping("product-code")
    public List<SpecificStatistic> statForProductCode() {
        return statisticService.statForProductCode(null, null);
    }

    @GetMapping("product-design")
    public List<SpecificStatistic> statForProductDesign() {
        return statisticService.statForProductDesign(null, null);
    }

    @GetMapping("seller")
    public List<SpecificStatistic> statForSeller() {
        return statisticService.statForSeller(null, null);
    }

    @GetMapping("supplier")
    public List<SpecificStatistic> statForSupplier() {
        return statisticService.statForSupplier(null, null);
    }
}
