package com.example.demo.domains.aggregate01.repository.persistence.mappers;

import com.example.demo.domains.aggregate01.entity.AddressDo;
import com.example.demo.domains.aggregate01.repository.po.AddressPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AddrDoMapper {
    AddrDoMapper INSTANCE =  Mappers.getMapper(AddrDoMapper.class);

    @Mappings({
            @Mapping(source = "addressPo.receiver", target = "receiverName"),
            @Mapping(source = "addressPo.phone", target = "receiverPhone")
    })
    AddressDo toDo(AddressPo addressPo);

    @Mappings({
            @Mapping(source = "addressDo.receiverName", target = "receiver"),
            @Mapping(source = "addressDo.receiverPhone", target = "phone")
    })
    AddressPo toPo(AddressDo addressDo);

    List<AddressPo> toPoList(List<AddressDo> addressDoList);
}
