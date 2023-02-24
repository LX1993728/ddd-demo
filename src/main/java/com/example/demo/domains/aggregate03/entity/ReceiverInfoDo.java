package com.example.demo.domains.aggregate03.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收货人信息——值对象
 */
@AllArgsConstructor
@Getter
public class ReceiverInfoDo {
    private String receiverName;
    private String receiverPhone;
    private String address;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
