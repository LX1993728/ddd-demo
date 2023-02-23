package com.example.demo.domains.aggregate01.service.impl;

import com.example.demo.domains.aggregate01.entity.AddressDo;
import com.example.demo.domains.aggregate01.entity.UserDo;
import com.example.demo.domains.aggregate01.repository.facade.UserDoRepository;
import com.example.demo.domains.aggregate01.repository.persistence.mappers.UserDoMapper;
import com.example.demo.domains.aggregate01.repository.po.AddressPo;
import com.example.demo.domains.aggregate01.repository.po.UserPo;
import com.example.demo.domains.aggregate01.service.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Resource
    private UserDoRepository userDoRepository;
    @Override
    public Optional<UserDo> getUserDoByUserId(Long userId) {
        log.info("进入领域服务 UserDomainServiceImpl.(userId={})", userId);
        return userDoRepository.getUserDoByUserId(userId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Optional<UserDo> registerUser(UserDo userDo) {
        return userDoRepository.saveUserDo(userDo);
    }

    public Optional<AddressDo> getDefaultAddr(UserDo userDo){
        return userDo.getDefaultAddrDo();
    }
}
