package com.example.demo.domains.aggregate02.repository.po;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品持久对象
 */
@Slf4j
@Data
@Entity
@Table(name = "product")
public class ProductPo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String name;
    @Column(columnDefinition = "bit(1) default 1")
    private Boolean onShelf; // 是否上架
    private String imageUrl;
    private String description; // 描述
    private Long stock;
    private Long price; // 分
    private Long version; // 乐观版本
}
