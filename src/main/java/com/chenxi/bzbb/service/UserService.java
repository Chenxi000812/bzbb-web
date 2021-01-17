package com.chenxi.bzbb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenxi.bzbb.domain.dao.OrderMapper;
import com.chenxi.bzbb.domain.dao.ShippingaddressMapper;
import com.chenxi.bzbb.domain.dao.UserMapper;
import com.chenxi.bzbb.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User loadUserByUsername(String phone){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public User autoRegistUser(String phone){
        User user = new User();
        user.setCreatetime(new Date());
        user.setPhone(phone);
        user.setRole("ROLE_USER");
        StringBuilder nicknamebuilder = new StringBuilder();
        nicknamebuilder.append("用户");
        nicknamebuilder.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        nicknamebuilder.append(new Random().nextInt(100));
        user.setNickname(nicknamebuilder.toString());
        userMapper.insertSelective(user);
        return user;
    }



}
