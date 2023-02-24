package com.example.demo.domains.aggregate03.entity;

import lombok.Data;

/**
 * 订单领域对象
 */
@Data
public class OrderItemDo {
    private Long id;
    private String orderId; //订单ID
    private Long goodCode; // 货物的编号，对应商品聚合中的商品ID
    private GoodInfoDo goodInfo; // 对应货物的信息详情
    private Long count; // 购买的数量
    private Long price; // 单件价格 单位：分

    // 计算此订单项的金额
    public Long calculateItemAmount(){
        return count * price;
    }

}
