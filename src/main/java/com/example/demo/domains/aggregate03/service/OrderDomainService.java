package com.example.demo.domains.aggregate03.service;

import com.example.demo.domains.aggregate03.entity.OrderDo;

import java.util.Optional;

public interface OrderDomainService {
    Optional<OrderDo> getOrder(OrderDo orderDo);

    /**
     * 创建订单
     * @param orderDo
     * @return
     */
    Optional<OrderDo> createOrder(OrderDo orderDo);
}
