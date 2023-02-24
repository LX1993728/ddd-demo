package com.example.demo.domains.aggregate03.repository.po;

import com.example.demo.domains.aggregate03.entity.OrderItemDo;
import com.example.demo.domains.aggregate03.entity.OrderStatus;
import com.example.demo.domains.aggregate03.entity.ReceiverInfoDo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 订单持久对象
 */

@Slf4j
@Data
@Entity
@Table(name = "t_order")
public class OrderPo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   //  private Long orderCode; // 订单编号 一般使用编号，在此省略
    private Long customerCode; // 顾客编号(购买者的ID)
    private String receiverInfo; // 收货人信息
    private Long biz; // 重试时的业务幂等号
    private Long amount; // 总金额
    private Integer orderStatus; // 订单状态
}
