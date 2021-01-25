package com.fly.myblog.service;

import com.fly.myblog.entity.User;

public interface UserService {

    //核对用户名密码
    User checkUser(String username,String password);
}
