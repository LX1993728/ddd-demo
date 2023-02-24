package com.example.demo.domains.aggregate03.repository.po;

import com.example.demo.domains.aggregate03.entity.GoodInfoDo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@Slf4j
@Data
@Entity
@Table(name = "order_item")
public class OrderItemPo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId; //订单ID
    private Long goodCode; // 货物的编号，对应商品聚合中的商品ID
    private String goodInfo; // 对应货物的信息详情
    private Long count; // 购买的数量
    private Long price; // 单件价格

}
