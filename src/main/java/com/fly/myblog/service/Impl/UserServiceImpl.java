package com.fly.myblog.service.Impl;

import com.fly.myblog.dao.UserDao;
import com.fly.myblog.entity.User;
import com.fly.myblog.service.UserService;
import com.fly.myblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
