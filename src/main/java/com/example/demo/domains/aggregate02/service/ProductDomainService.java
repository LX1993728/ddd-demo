package com.example.demo.domains.aggregate02.service;

import com.example.demo.domains.aggregate02.entity.ProductDo;

import java.util.Optional;

public interface ProductDomainService {
    Optional<ProductDo> getProduct(ProductDo productDo);

    /**
     * 增加商品
     * @param productDo
     * @return
     */
    Optional<ProductDo> saveProduct(ProductDo productDo);

    /**
     * 扣减更新库存
     * @param productDo
     * @return
     */
    Boolean reduceStock(ProductDo productDo, Long num);
}
