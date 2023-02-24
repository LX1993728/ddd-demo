package com.example.demo.domains.aggregate03.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 值对象 —— 映射对应的商品信息
 * 整体设置 不可修改
 */
@AllArgsConstructor
@Getter
public class GoodInfoDo {
    private String goodName; // 货物名称
    private String imageUrl;// 图片名称
    private String description; // 描述

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
