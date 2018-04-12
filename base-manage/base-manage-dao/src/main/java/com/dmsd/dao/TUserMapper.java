package com.dmsd.dao;

import com.dmsd.pojo.TUser;

public interface TUserMapper {
    TUser selectByPrimaryKey(int id);
}
