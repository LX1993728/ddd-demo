package com.example.demo.domains.aggregate03.entity;

import lombok.Data;

import java.util.List;

/**
 * 订单领域对象 —— 也是此订单聚合的聚合根
 */
@Data
public class OrderDo {
    private Long id;
    private Long customerCode; // 顾客编号(购买者的ID)
    private ReceiverInfoDo receiverInfo; // 收货人信息
    List<OrderItemDo> orderItems; // 订单列表项
    private Long biz; // 重试时的业务幂等号
    private Long amount;
    private OrderStatus orderStatus; // 订单状态

    /**
     * 计算此订单的总金额
     */
    public Long calculateOrderAmount(){
        // TODO: 校验
         Long amount = 0L;
         for (OrderItemDo itemDo : this.getOrderItems()){
             amount+= itemDo.calculateItemAmount();
         }
         return amount;
    }

}
