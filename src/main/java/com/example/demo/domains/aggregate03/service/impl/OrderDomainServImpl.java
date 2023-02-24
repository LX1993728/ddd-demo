package com.example.demo.domains.aggregate03.service.impl;

import com.example.demo.domains.aggregate03.entity.OrderDo;
import com.example.demo.domains.aggregate03.entity.OrderStatus;
import com.example.demo.domains.aggregate03.repository.facade.OrderRepository;
import com.example.demo.domains.aggregate03.service.OrderDomainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class OrderDomainServImpl implements OrderDomainService {
    @Resource
    private OrderRepository orderRepository;

    @Override
    public Optional<OrderDo> getOrder(OrderDo orderDo) {
        return orderRepository.getOrder(orderDo);
    }

    @Override
    public Optional<OrderDo> createOrder(OrderDo orderDo) {
        orderDo.setOrderStatus(OrderStatus.CREATE);
        orderDo.setAmount(orderDo.calculateOrderAmount());

        return orderRepository.createOrder(orderDo);
    }
}
