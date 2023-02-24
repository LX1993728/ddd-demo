package com.example.demo.domains.aggregate01.repository.persistence;

import com.example.demo.domains.aggregate01.entity.UserDo;
import com.example.demo.domains.aggregate01.repository.dao.AddressDao;
import com.example.demo.domains.aggregate01.repository.dao.UserDao;
import com.example.demo.domains.aggregate01.repository.facade.UserRepository;
import com.example.demo.domains.aggregate01.repository.persistence.mappers.AddrDoMapper;
import com.example.demo.domains.aggregate01.repository.persistence.mappers.UserDoMapper;
import com.example.demo.domains.aggregate01.repository.po.AddressPo;
import com.example.demo.domains.aggregate01.repository.po.UserPo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class UerRepImpl implements UserRepository {
    @Resource
    private UserDao userDao;
    @Resource
    private AddressDao addressDao;

    @Override
    public Optional<UserDo> getUserDoByUserId(UserDo userDo) {
        Assert.isTrue(userDo != null && userDo.getUserId() != null, "userId cannot be null");
        Optional<UserPo> userPoOpt = userDao.findById(userDo.getUserId());
        if (!userPoOpt.isPresent()){
            return Optional.empty();
        }
        List<AddressPo> addrPos = addressDao.findAllByUserId(userDo.getUserId());
        UserDo userDoResult = UserDoMapper.INSTANCE.toDo(addrPos, userPoOpt.get());

        return Optional.of(userDoResult);
    }

    @Override
    public Optional<UserDo> saveUserDo(UserDo userDo) {
        Assert.notNull(userDo, "userDo cannot be null");
        UserPo userPo = UserDoMapper.INSTANCE.toPo(userDo);
        Long userId = userDao.save(userPo).getId();
        if (CollectionUtils.isNotEmpty(userDo.getAddressDoList())){
            List<AddressPo> addressPoList = AddrDoMapper.INSTANCE.toPoList(userDo.getAddressDoList());
            addressPoList.forEach(addressPo -> addressPo.setUserId(userId));
            addressDao.saveAll(addressPoList);
        }
        return getUserDoByUserId(new UserDo(){{
            setUserId(userId);
        }});
    }

}
