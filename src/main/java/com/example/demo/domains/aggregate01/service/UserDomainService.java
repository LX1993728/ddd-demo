package com.example.demo.domains.aggregate01.service;

import com.example.demo.domains.aggregate01.entity.UserDo;

import java.util.Optional;

public interface UserDomainService {
    Optional<UserDo> getUserDoByUserId(Long userId);
    Optional<UserDo> registerUser(UserDo userDo);
}
