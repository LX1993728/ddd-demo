package com.example.demo.domains.aggregate02.service.impl;

import com.example.demo.domains.aggregate02.entity.ProductDo;
import com.example.demo.domains.aggregate02.repository.facade.ProductRepository;
import com.example.demo.domains.aggregate02.service.ProductDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Service
public class ProductDmServImpl implements ProductDomainService {
    @Resource
    private ProductRepository productRepository;
    @Override
    public Optional<ProductDo> getProduct(ProductDo productDo) {
        return productRepository.getProduct(productDo);
    }

    @Override
    public Optional<ProductDo> saveProduct(ProductDo productDo) {
        return productRepository.saveProduct(productDo);
    }

    @Override
    public Boolean reduceStock(ProductDo productDo, Long num) {
        Assert.notNull(productDo, "参数不可为空");
        Boolean preRes = productDo.reduceStock(num);
        if (!preRes){
            log.error("业务繁忙，请稍后重试");
            return false;
        }
        // 模拟消息或异步更新库存
        return productRepository.reduceStock(productDo, num);
    }
}
