package com.printway.business.services;

import com.printway.business.dto.OrderDTO;
import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.dto.statistic.SpecificStatistic;
import com.printway.business.repositories.OrderProductRepository;
import com.printway.business.repositories.OrderRepository;
import com.printway.common.json.RestResponsePage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<OrderDTO> listOrders(int page, int limit, String order, String orderBy) {
        var sort = order.equals("asc")
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        var orders = orderRepository.findAll(PageRequest.of(page, limit, sort));
        var typeRef = new TypeReference<RestResponsePage<OrderDTO>>() {};
        return objectMapper.convertValue(orders, typeRef);
    }

    @Override
    public OrderDTO getOrderDetails(String orderCode) {
        var products = orderProductRepository.findByOrderCode(orderCode);
        var order = orderRepository.findByCode(orderCode);
        var orderDto = objectMapper.convertValue(order, OrderDTO.class);
        orderDto.setProducts(products);
        return orderDto;
    }

    @Override
    public List<SummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to) {
        return orderRepository.statForSummary(from, to);
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
