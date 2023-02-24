package com.example.demo.controllers.facade;

import com.example.demo.application.service.OrderAppService;
import com.example.demo.controllers.dto.CreateOrderDTO;
import com.example.demo.controllers.vo.CreateOrderVo;
import com.example.demo.domains.aggregate03.entity.OrderDo;
import com.example.demo.domains.aggregate03.entity.OrderItemDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderAppService orderAppService;

    @GetMapping("/create")
//    public CreateOrderDTO CreateOrderDTO(@RequestBody CreateOrderVo vo){ 此处只是模拟
    public Object TestCreateOrderDTO(){
        // TODO: 参数校验 在此处只做测试
        CreateOrderVo vo =  new CreateOrderVo();
        vo.setBiz(111L);
        vo.setAddressId(1L);
        vo.setUserId(1L);
        Map<Long, Long>  itemMap = new HashMap<>();
        itemMap.put(1L, 2L);
        itemMap.put(2L, 3L);

        // 在此模拟 参数组装映射
        OrderDo orderDo = new OrderDo();
        orderDo.setBiz(vo.getBiz());
        orderDo.setCustomerCode(vo.getUserId());
        List<OrderItemDo> itemDoList = new ArrayList<>();
        for (Map.Entry<Long,Long> entry : itemMap.entrySet()){
            itemDoList.add(new OrderItemDo(){{
                setGoodCode(entry.getKey());
                setCount(entry.getValue());
            }});
        }
        orderDo.setOrderItems(itemDoList);

        Optional<OrderDo> orderDoOpt = orderAppService.createOrder(orderDo, vo.getAddressId());
        if (orderDoOpt.isPresent()){
            return orderDoOpt.get();
        }

        return "create order fail";
    }
}
