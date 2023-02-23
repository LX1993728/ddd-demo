package com.example.demo.domains.aggregate01.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 领域对象 也是此聚合的聚合根
 */
@NoArgsConstructor
@Data
public class UserDo {
    private Long userId;
    private String userName;
    private String email;
    private String telephone;
    private Integer gender;
    private Date birth;
    List<AddressDo> addressDoList;

    // 获取默认的收货人地址
    public Optional<AddressDo> getDefaultAddrDo(){
        if (CollectionUtils.isNotEmpty(addressDoList)){
            return addressDoList.stream().findFirst();
        }
        return Optional.empty();
    }

}
