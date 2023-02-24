package com.example.demo.domains.aggregate01.repository.facade;

import com.example.demo.domains.aggregate01.entity.UserDo;

import java.util.Optional;

/**
 * 仓促对象 领域对象与  持久化对象的聚合转换
 * 仓储接口方法参数 以及 领域服务方法的参数 统一为当前聚合的聚合根领域对象
 */
public interface UserRepository {
    /**
     * 获取聚合根
     * @param userDo
     * @return
     */
    Optional<UserDo> getUserDoByUserId(UserDo userDo);

    /**
     * 注册用户
     * @param userDo
     * @return
     */
    Optional<UserDo> saveUserDo(UserDo userDo);
}
