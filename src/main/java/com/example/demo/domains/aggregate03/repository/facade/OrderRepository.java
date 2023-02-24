package com.example.demo.domains.aggregate03.repository.facade;

import com.example.demo.domains.aggregate03.entity.OrderDo;

import java.util.Optional;

/**
 * 订单聚合仓储接口
 */
public interface OrderRepository {
    /**
     * 根据约定的条件查询订单
     * @param orderDo
     * @return
     */
    Optional<OrderDo> getOrder(OrderDo orderDo);

    /**
     * 创建订单
     * @param orderDo
     * @return
     */
    Optional<OrderDo> createOrder(OrderDo orderDo);
}
