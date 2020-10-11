package com.printway.business.services;

import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.dto.statistic.SpecificStatistic;
import com.printway.business.repositories.OrderProductRepository;
import com.printway.business.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public List<SummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to, Integer storeId) {
        return orderRepository.statForSummary(from, to, storeId);
    }

    @Override
    public List<SpecificStatistic> statForProductSku(LocalDateTime from, LocalDateTime to) {
        return orderProductRepository.statForSku(from, to);
    }

    @Override
    public List<SpecificStatistic> statForProductCode(LocalDateTime from, LocalDateTime to) {
        return orderProductRepository.statForProductCode(from, to);
    }

    @Override
    public List<SpecificStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to) {
        return orderProductRepository.statForDesignCode(from, to);
    }

    @Override
    public List<SpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to) {
        return orderProductRepository.statForSupplier(from, to);
    }

    @Override
    public List<SpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to) {
        return orderProductRepository.statForSeller(from, to);
    }

}
