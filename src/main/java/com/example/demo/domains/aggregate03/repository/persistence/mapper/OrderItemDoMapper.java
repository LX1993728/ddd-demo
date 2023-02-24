package com.example.demo.domains.aggregate03.repository.persistence.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domains.aggregate03.entity.GoodInfoDo;
import com.example.demo.domains.aggregate03.entity.OrderItemDo;
import com.example.demo.domains.aggregate03.repository.po.OrderItemPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderItemDoMapper {
    OrderItemDoMapper INSTANCE = Mappers.getMapper(OrderItemDoMapper.class);
    @Mapping(target = "goodInfo", expression = "java(convertStrToGood(itemPo.getGoodInfo()))")
    OrderItemDo toDo(OrderItemPo itemPo);

    @Mapping(target = "goodInfo", expression = "java(itemDo.getGoodInfo().toString())")
    OrderItemPo toPo(OrderItemDo itemDo);

    List<OrderItemPo> toPoList(List<OrderItemDo> itemDoList);

    default GoodInfoDo convertStrToGood(String jsonStr){
        return JSONObject.parseObject(jsonStr, GoodInfoDo.class);
    }
}
