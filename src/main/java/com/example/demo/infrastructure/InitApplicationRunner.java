package com.example.demo.infrastructure;

import com.example.demo.domains.aggregate01.entity.AddressDo;
import com.example.demo.domains.aggregate01.entity.UserDo;
import com.example.demo.domains.aggregate01.service.UserDomainService;
import com.example.demo.domains.aggregate02.entity.ProductDo;
import com.example.demo.domains.aggregate02.service.ProductDomainService;
import com.example.demo.domains.aggregate03.service.OrderDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class InitApplicationRunner implements ApplicationRunner {
    @Resource
    private UserDomainService userDomainService;
    @Resource
    private ProductDomainService productDomainService;
    @Resource
    private OrderDomainService orderDomainService;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void run(ApplicationArguments args) throws Exception {
        log.info("程序已启动，正在初始化数据中......");
        UserDo userDo = new UserDo();
        userDo.setUserName("张三");
        userDo.setGender(1);
        userDo.setEmail("1234567@qq.com");
        userDo.setBirth(new Date());
        userDo.setTelephone("18737652345");
        userDo.setAddressDoList(new ArrayList<AddressDo>(){{
            add(new AddressDo(null, null,"收货人1","123456","中国","河南", "周口", "太康县"));
            add(new AddressDo(null, null,"收货人2","7890","中国","北京", "北京", "昌平区"));
        }});
        Optional<UserDo> userDoOpt = userDomainService.registerUser(userDo);
        if (!userDoOpt.isPresent()){
            throw new IllegalStateException("初始化用户数据失败");
        }
        log.info("初始化用户数据成功");
        ProductDo productDo1 = new ProductDo(null, "MacBook Pro", true, 10000L, "http://xxx.png", "15英寸苹果本",1L, 1800000L);
        ProductDo productDo2 = new ProductDo(null, "iphone16 PLUS", true, 10000L, "http://yyy.png", "最新版苹果手机",1L, 600000L);
        ProductDo productDo3 = new ProductDo(null, "IPaiD Mini", true, 10000L, "http://zzz.png", "最新版苹果平板电脑",1L, 300000L);
        productDomainService.saveProduct(productDo1);
        productDomainService.saveProduct(productDo2);
        productDomainService.saveProduct(productDo3);
        log.info("初始化商品信息成功");

        log.info("程序数据已经初始化完毕");
    }
}
