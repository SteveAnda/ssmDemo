package com.dmsd.service;

import com.dmsd.pojo.TUser;
import com.dmsd.api.UserService;
import com.dmsd.dao.TUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    TUserMapper tUserMapper;

    public TUser findUserById(int id) {
        return tUserMapper.selectByPrimaryKey(id);
    }
}
