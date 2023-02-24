package com.example.demo.domains.aggregate03.repository.persistence.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domains.aggregate03.entity.OrderDo;
import com.example.demo.domains.aggregate03.entity.OrderStatus;
import com.example.demo.domains.aggregate03.entity.ReceiverInfoDo;
import com.example.demo.domains.aggregate03.repository.po.OrderItemPo;
import com.example.demo.domains.aggregate03.repository.po.OrderPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {OrderItemDoMapper.class})
public interface OrderDoMapper {
    OrderDoMapper INSTANCE = Mappers.getMapper(OrderDoMapper.class);

    @Mappings({
            @Mapping(source = "itemPoList", target = "orderItems"),
            @Mapping(target = "receiverInfo", expression =
                    "java(convertStrToReceiver(orderPo.getReceiverInfo()))"),
            @Mapping(target = "orderStatus", expression = "java(setOrderStatusByCode(orderPo.getOrderStatus()))")
    })
    OrderDo toDo(List<OrderItemPo> itemPoList, OrderPo orderPo);


    @Mappings({
            @Mapping(target = "receiverInfo", expression = "java(orderDo.getReceiverInfo().toString())"),
            @Mapping(target = "orderStatus", expression = "java(orderDo.getOrderStatus().getCode())")
    })
    OrderPo toPo(OrderDo orderDo);

    default ReceiverInfoDo  convertStrToReceiver(String jsonStr){
        return JSONObject.parseObject(jsonStr, ReceiverInfoDo.class);
    }

    default OrderStatus setOrderStatusByCode(int code){
        return OrderStatus.getOrderStatus(code);
    }
}
