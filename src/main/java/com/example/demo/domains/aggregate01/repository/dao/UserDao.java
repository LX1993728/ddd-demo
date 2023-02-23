package com.example.demo.domains.aggregate01.repository.dao;

import com.example.demo.domains.aggregate01.repository.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserPo, Long> {
}
