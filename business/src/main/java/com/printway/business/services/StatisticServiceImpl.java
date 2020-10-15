package com.printway.business.services;

import com.printway.business.dto.statistic.SellerStatistic;
import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.dto.statistic.ProductTypeStatistic;
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
    public List<ProductTypeStatistic> statForProductType(LocalDateTime from, LocalDateTime to, Integer storeId) {
        return orderProductRepository.statForProductType(from, to, storeId);
    }

    @Override
    public List<ProductTypeStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to, Integer storeId) {
        return orderProductRepository.statForProductDesign(from, to, storeId);
    }

    @Override
    public List<ProductTypeStatistic> statForSupplier(LocalDateTime from, LocalDateTime to, Integer storeId) {
        return orderProductRepository.statForSupplier(from, to, storeId);
    }

    @Override
    public List<SellerStatistic> statForSeller(LocalDateTime from, LocalDateTime to, Integer storeId) {
        return orderProductRepository.statForSeller(from, to, storeId);
    }

}
