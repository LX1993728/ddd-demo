package com.example.demo;

import com.example.demo.domains.aggregate01.entity.AddressDo;
import com.example.demo.domains.aggregate01.entity.UserDo;
import com.example.demo.domains.aggregate01.service.UserDomainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * 临时测试初始化
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private UserDomainService userDomainService;
    @GetMapping("/init")
    public Object initData(){
        UserDo userDo = new UserDo();
        userDo.setUserName("张三");
        userDo.setGender(1);
        userDo.setEmail("1234567@qq.com");
        userDo.setBirth(new Date());
        userDo.setTelephone("18737652345");
        userDo.setAddressDoList(new ArrayList<AddressDo>(){{
            add(new AddressDo("收货人1","123456","中国","河南", "周口"));
            add(new AddressDo("收货人2","7890","中国","北京", "北京"));
        }});
        Optional<UserDo> userDoOpt = userDomainService.registerUser(userDo);
        return userDoOpt.isPresent() ? userDoOpt.get() : "注册失败";
    }
}

