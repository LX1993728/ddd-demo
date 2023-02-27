package com.example.demo.domains.aggregate02.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 商品领域对象
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDo {
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品是否上架
     */
    private Boolean onShelf;
    /**
     * 商品库存
     */
    private Long stock;

    private String imageUrl;
    private String description; // 描述
    private Long version;
    private Long price; // 分

    /**
     * 真实项目中一个商品对应多个SKU
     *  private List<Sku> skuList;
     */

    /**
     * 扣减库存
     * @param num
     * @return
     */
    public Boolean reduceStock(Long num){
        if (num > stock){
            log.warn("扣减量{}不可以大于库存量{}", num, stock);
            return false;
        }
        /**
         * 使用RedLock 或 Hash的HDECRBY指令+Double Check扣减库存
         * .....
         * 此处仅模拟
         */

        return true;
    }

}
