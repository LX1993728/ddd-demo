package com.example.demo.domains.aggregate01.repository.facade;

import com.example.demo.domains.aggregate01.entity.UserDo;

import java.util.Optional;

/**
 * 仓促对象 领域对象与  持久化对象的聚合转换
 */
public interface UserDoRepository {
    /**
     * 获取聚合根
     * @param userId
     * @return
     */
    Optional<UserDo> getUserDoByUserId(Long userId);

    /**
     * 注册用户
     * @param userDo
     * @return
     */
    Optional<UserDo> saveUserDo(UserDo userDo);
}
