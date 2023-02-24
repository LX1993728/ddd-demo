package com.example.demo.application.service;

import com.example.demo.domains.aggregate01.entity.AddressDo;
import com.example.demo.domains.aggregate01.entity.UserDo;
import com.example.demo.domains.aggregate01.service.UserDomainService;
import com.example.demo.domains.aggregate02.entity.ProductDo;
import com.example.demo.domains.aggregate02.service.ProductDomainService;
import com.example.demo.domains.aggregate03.entity.GoodInfoDo;
import com.example.demo.domains.aggregate03.entity.OrderDo;
import com.example.demo.domains.aggregate03.entity.OrderItemDo;
import com.example.demo.domains.aggregate03.entity.ReceiverInfoDo;
import com.example.demo.domains.aggregate03.service.OrderDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Service
public class OrderAppService {
    @Resource
    private UserDomainService userDomainService;
    @Resource
    private ProductDomainService productDomainService;
    @Resource
    private OrderDomainService orderDomainService;

    public Optional<OrderDo> createOrder(OrderDo orderDo, Long addressId){
        // 做些用户校验 一类的业务逻辑
        Assert.notNull(orderDo, "参数不能为空");
        Assert.notNull(orderDo.getCustomerCode(), "用户ID不能为空");
        Assert.isTrue(CollectionUtils.isNotEmpty(orderDo.getOrderItems()), "订单项不能为空");

        Optional<UserDo> userOpt = userDomainService.getUserDoByUserId(new UserDo() {{
            setUserId(orderDo.getCustomerCode());
        }});

        if (!userOpt.isPresent()){
            throw new IllegalStateException("该用户不存在");
        }

        UserDo userDo = userOpt.get();
        Optional<AddressDo> addrOpt = null;
        if (addressId == null){
            addrOpt = userDo.getDefaultAddrDo();
        }else {
            addrOpt = userDo.getAddressDoList().stream().filter(addrDo-> addrDo.getId().equals(addressId)).findFirst();
        }

        Assert.isTrue(addrOpt.isPresent(), "需要绑定一个收货人地址信息");
        AddressDo addrDo = addrOpt.get();
        orderDo.setReceiverInfo(new ReceiverInfoDo(addrDo.getReceiverName(),addrDo.getReceiverPhone(), addrDo.getAddrStr()));

        // 扣减库存并设置信息
        for (OrderItemDo itemDo : orderDo.getOrderItems()){
            Optional<ProductDo> productOpt = productDomainService.getProduct(new ProductDo() {{
                setProductId(itemDo.getGoodCode());
            }});
            Assert.isTrue(productOpt.isPresent(), "存在非法商品");
            ProductDo productDo = productOpt.get();
            Boolean reduceSuccess = productDomainService.reduceStock(productOpt.get(), itemDo.getCount());
            Assert.isTrue(reduceSuccess, String.format("商品 %s 扣减库存 %s 失败", itemDo.getGoodCode(), itemDo.getCount()));
            // 设置订单项信息
            itemDo.setGoodInfo(new GoodInfoDo(productDo.getProductName(), productDo.getImageUrl(), productDo.getDescription()));
            itemDo.setPrice(productDo.getPrice());
        }

        return orderDomainService.createOrder(orderDo);

    }

    public Optional<UserDo> getUserDoByUserId(UserDo userDo){
        return userDomainService.getUserDoByUserId(userDo);
    }
}
