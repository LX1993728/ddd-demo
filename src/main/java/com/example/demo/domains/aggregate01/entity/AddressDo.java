package com.example.demo.domains.aggregate01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 领域对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDo {
    private Long id;

    // 关联用户
    private Long userId;
    // 收货人姓名
    private String receiverName;
    // 收货人电话
    private String receiverPhone;
    // 收货人地址
    private String country;
    private String province;
    private String city;
    private String detail;

    // 处理非持久化的职责业务方法
    // ......
    public String getAddrStr(){
        return String.format("%s %s %s 具体住址: %s", country, province, city, detail);
    }
}
