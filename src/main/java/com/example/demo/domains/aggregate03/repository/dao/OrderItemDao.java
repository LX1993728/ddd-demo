package com.example.demo.domains.aggregate03.repository.dao;

import com.example.demo.domains.aggregate03.repository.po.OrderItemPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderItemDao extends JpaRepository<OrderItemPo, Long> {
    List<OrderItemPo> findAllByOrderId(Long orderId);
}
