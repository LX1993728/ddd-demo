package com.example.demo.domains.aggregate03.repository.dao;

import com.example.demo.domains.aggregate03.repository.po.OrderPo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDao extends JpaRepository<OrderPo, Long> {
}
