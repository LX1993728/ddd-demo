package com.example.demo.domains.aggregate03.repository.persistence;

import com.example.demo.domains.aggregate03.entity.OrderDo;
import com.example.demo.domains.aggregate03.entity.OrderStatus;
import com.example.demo.domains.aggregate03.repository.dao.OrderDao;
import com.example.demo.domains.aggregate03.repository.dao.OrderItemDao;
import com.example.demo.domains.aggregate03.repository.facade.OrderRepository;
import com.example.demo.domains.aggregate03.repository.persistence.mapper.OrderDoMapper;
import com.example.demo.domains.aggregate03.repository.persistence.mapper.OrderItemDoMapper;
import com.example.demo.domains.aggregate03.repository.po.OrderItemPo;
import com.example.demo.domains.aggregate03.repository.po.OrderPo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 组装 领域对象 只做校验和持久化
 */
@Slf4j
@Component
public class OrderRepoImpl implements OrderRepository {
    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemDao orderItemDao;

    @Override
    public Optional<OrderDo> getOrder(OrderDo orderDo) {
        // TODO: 根据具体查询条件进行校验
        Assert.isTrue(orderDo != null && orderDo.getId() != null, "参数及id不能为空");
        Optional<OrderPo> orderPoOpt = orderDao.findById(orderDo.getId());
        if (orderPoOpt.isPresent()){
            List<OrderItemPo> itemPos = orderItemDao.findAllByOrderId(orderDo.getId());
            return Optional.of(OrderDoMapper.INSTANCE.toDo(itemPos, orderPoOpt.get()));
        }

        return Optional.empty();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Optional<OrderDo> createOrder(OrderDo orderDo) {
        // TODO: 进行关键字段校验
        Assert.isTrue(CollectionUtils.isNotEmpty(orderDo.getOrderItems()), "参数不合法");
        List<OrderItemPo> orderItemPos = OrderItemDoMapper.INSTANCE.toPoList(orderDo.getOrderItems());
        orderItemPos.forEach(itemPo -> itemPo.setOrderId(orderDo.getId()));
        orderItemDao.saveAll(orderItemPos);

        OrderPo orderPo = OrderDoMapper.INSTANCE.toPo(orderDo);
        orderDao.save(orderPo);

        return getOrder(orderDo);
    }
}
