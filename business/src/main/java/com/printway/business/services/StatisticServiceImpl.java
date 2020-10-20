package com.printway.business.services;

import com.printway.business.dto.statistic.*;
import com.printway.business.models.MarketingFee;
import com.printway.business.repositories.MarketingFeeRepository;
import com.printway.business.repositories.OrderProductRepository;
import com.printway.business.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private MarketingFeeRepository marketingFeeRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public List<SummaryStatistic> statForSummary(StatisticQueryParam params) {
        var stats = orderRepository.statForSummary(params);
        return stats.stream().peek(stat -> {
            var date = LocalDateTime.of(stat.getYear(), stat.getMonth(), stat.getDay(), 0, 0, 0);
            var mktFees = params.getSellerCode().equals("")
                ? marketingFeeRepository.findAllByStartTimeLessThanEqualAndStopTimeGreaterThanEqual(date, date)
                : marketingFeeRepository.findAllBySellerCodeAndStartTimeLessThanEqualAndStopTimeGreaterThanEqual(params.getSellerCode(), date, date);
            var mktFee = mktFees.stream().map(MarketingFee::getSpendPerDay).reduce(Double::sum).orElse(null);
            stat.setMarketingFee(mktFee == null ? null : Math.round(mktFee * 103) / 100.00);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductTypeStatistic> statForProductType(StatisticQueryParam params) {
        return orderProductRepository.statForProductType(params);
    }

    @Override
    public List<ProductDesignStatistic> statForProductDesign(StatisticQueryParam params) {
        var accList = accountService.listAccount().getContent();
        return orderProductRepository.statForProductDesign(params)
                .stream()
                .peek(stat -> {
                    if (stat.getSellerName() == null)
                        return;

                    var sellerInfo = accList.stream()
                            .filter(u -> u.getCode().toLowerCase().equals(stat.getSellerName().toLowerCase()))
                            .findFirst().orElse(null);
                    if (sellerInfo == null)
                        return;
                    
                    stat.setSellerName(sellerInfo.getFullname());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierStatistic> statForSupplier(StatisticQueryParam params) {
        return orderProductRepository.statForSupplier(params);
    }

    @Override
    public List<SellerStatistic> statForSeller(StatisticQueryParam params) {
        var accList = accountService.listAccount().getContent();
        return orderProductRepository.statForSeller(params)
                .stream()
                .peek(stat -> {
                    if (stat.getName() == null)
                        return;

                    var sellerInfo = accList.stream()
                            .filter(u -> u.getCode().toLowerCase().equals(stat.getName().toLowerCase()))
                            .findFirst().orElse(null);
                    
                    if (sellerInfo == null)
                        return;

                    var revenueProfit = stat.getRevenue()
                            - (stat.getBaseCostFee() != null ? stat.getBaseCostFee() : 0)
                            - (stat.getMarketingFee() != null ? stat.getMarketingFee() : 0)
                            - (stat.getStoreFee() != null ? stat.getStoreFee() : 0);
                    var profitRate = 0D;
                    var bonus = 0D;

                    if (revenueProfit < 400) {
                        bonus = sellerInfo.getBonus1();
                    } else if (revenueProfit < 1000) {
                        bonus = sellerInfo.getBonus2();
                    } else if (revenueProfit < 3000) {
                        bonus = sellerInfo.getBonus3();
                    } else if (revenueProfit < 5000) {
                        bonus = sellerInfo.getBonus4();
                    } else {
                        bonus = sellerInfo.getBonus5();
                    }

                    if (revenueProfit < 650) {
                        profitRate = sellerInfo.getProfit1();
                    } else if (revenueProfit < 2500) {
                        profitRate = sellerInfo.getProfit2();
                    } else if (revenueProfit < 5000) {
                        profitRate = sellerInfo.getProfit3();
                    } else if (revenueProfit < 10000) {
                        profitRate = sellerInfo.getProfit4();
                    } else {
                        profitRate = sellerInfo.getProfit5();
                    }

                    stat.setName(sellerInfo.getFullname());
                    stat.setNetProfit(Math.round(revenueProfit * profitRate *100) / 100.00);
                    stat.setBonus(Math.round(bonus * stat.getOrderCount() * 100) / 100.00);
                    stat.setSellerProfit(Math.round((stat.getNetProfit() + stat.getBonus()) * 100) / 100.00);
                })
                .collect(Collectors.toList());
    }

}
