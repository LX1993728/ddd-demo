package com.example.demo.domains.aggregate02.repository.persistence.mappers;

import com.example.demo.domains.aggregate02.entity.ProductDo;
import com.example.demo.domains.aggregate02.repository.po.ProductPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductDoMapper {
    ProductDoMapper INSTANCE = Mappers.getMapper(ProductDoMapper.class);
    @Mappings({
            @Mapping(source = "productPo.id", target = "productId"),
            @Mapping(source = "productPo.name", target = "productName")
    })
    ProductDo toDo(ProductPo productPo);

    @Mappings({
            @Mapping(source = "productDo.productId", target = "id"),
            @Mapping(source = "productDo.productName", target = "name")
    })
    ProductPo toPo(ProductDo productDo);
}
