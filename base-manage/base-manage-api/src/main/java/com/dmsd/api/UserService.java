package com.dmsd.api;

import com.dmsd.pojo.TUser;

public interface UserService {
    TUser findUserById(int id);
}
