package com.example.demo.domains.aggregate02.repository.persistence;

import com.example.demo.domains.aggregate02.entity.ProductDo;
import com.example.demo.domains.aggregate02.repository.dao.ProductDao;
import com.example.demo.domains.aggregate02.repository.facade.ProductRepository;
import com.example.demo.domains.aggregate02.repository.persistence.mappers.ProductDoMapper;
import com.example.demo.domains.aggregate02.repository.po.ProductPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 仓储实现 —— 仅进行参数校验和持久对象与领域对象之间的转换
 * 不做具体的业务逻辑
 */
@Slf4j
@Component
public class ProductRepImpl implements ProductRepository {
    @Resource
    private ProductDao productDao;
    @Override
    public Optional<ProductDo> getProduct(ProductDo productDo) {
        Assert.isTrue(productDo != null && productDo.getProductId() != null, "productId不能为空");
        ProductPo productPo = productDao.getProductPo(productDo.getProductId());
        return Optional.of(ProductDoMapper.INSTANCE.toDo(productPo));
    }

    @Override
    public Optional<ProductDo> saveProduct(ProductDo productDo) {
        // TODO 做关键字段校验
        ProductPo productPo = productDao.save(ProductDoMapper.INSTANCE.toPo(productDo));
        return Optional.of(ProductDoMapper.INSTANCE.toDo(productPo));
    }

    @Override
    public Boolean reduceStock(ProductDo productDo, Long num) {
        // TODO 进行校验
        int i = productDao.reduceStock(productDo.getProductId(), num);
        return i > 0;
    }
}
