package com.example.demo.domains.aggregate01.repository.dao;

import com.example.demo.domains.aggregate01.repository.po.AddressPo;
import com.example.demo.domains.aggregate01.repository.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressDao extends JpaRepository<AddressPo, Long> {
    List<AddressPo> findAllByUserId(Long userId);
}
