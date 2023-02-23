package com.example.demo.domains.aggregate01.repository.persistence.mappers;

import com.example.demo.domains.aggregate01.entity.UserDo;
import com.example.demo.domains.aggregate01.repository.po.AddressPo;
import com.example.demo.domains.aggregate01.repository.po.UserPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 此聚合内PO 与DO之间的映射转换层
 */
@Mapper(uses = {AddrDoMapper.class})
public interface UserDoMapper {
  UserDoMapper INSTANCE = Mappers.getMapper(UserDoMapper.class);
  @Mapping(source = "addressPos", target = "addressDoList")
  @Mapping(source = "userPo.id", target = "userId")
  @Mapping(source = "userPo.name", target = "userName")
  @Mapping(source = "userPo.phone", target = "telephone")
  UserDo toDo(List<AddressPo> addressPos, UserPo userPo);

  @Mapping(source = "userDo.userId", target = "id")
  @Mapping(source = "userDo.userName", target = "name")
  @Mapping(source = "userDo.telephone", target = "phone")
  UserPo toPo(UserDo userDo);
}
