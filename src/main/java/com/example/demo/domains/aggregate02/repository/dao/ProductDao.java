package com.example.demo.domains.aggregate02.repository.dao;

import com.example.demo.domains.aggregate02.repository.po.ProductPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductDao extends JpaRepository<ProductPo,Long> {
    /**
     * 更新库存 (不使用分布式锁)
     * @param productId
     * @param num
     * @param version
     * @return
     */
    @Modifying
    @Query("update ProductPo p set p.stock=p.stock- ?2, p.version = p.version+1 " +
            "where p.id = ?1 and p.stock >= ?2 and p.version = ?3")
    int reduceStock(Long productId, Long num, Long version);

    /**
     * 更新库存（使用分布式锁保证）
     * @param productId
     * @param num
     * @return
     */
    @Modifying
    @Query("update ProductPo p set p.stock=p.stock- ?2, p.version = p.version+1 " +
            "where p.id = ?1 and p.stock >= ?2")
    int reduceStock(Long productId, Long num);

    @Query("select p from ProductPo  p where p.id=?1 and p.onShelf=true ")
    ProductPo getProductPo(Long productId);
}
