package com.example.demo.controllers.vo;

import lombok.Data;

import java.util.Map;

@Data
public class CreateOrderVo {
    private Long userId; // 一般从Token或session中获取，此处省略
    private Long biz; // 业务幂等号标识
    private Long addressId; // 收货人地址项 ID
    Map<Long, Long> itemMap;
}

