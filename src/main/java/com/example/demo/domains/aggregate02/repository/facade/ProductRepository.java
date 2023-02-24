package com.example.demo.domains.aggregate02.repository.facade;

import com.example.demo.domains.aggregate02.entity.ProductDo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ProductRepository {
    /**
     * 获取商品对象
     * @param productDo
     * @return
     */
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
