package com.csu.service.impl;

import com.csu.bean.User;
import com.csu.dao.UserDao;
import com.csu.dao.impl.UserDaoImpl;
import com.csu.service.UserService;
import com.csu.utils.MD5Util;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        //1. 加密
        user.setPassword(MD5Util.encode(user.getPassword()));
        //2. 保存
        return userDao.addUser(user);
    }

    @Override
    public User login(String username, String password) {
        //1.根据用户名查找User对象
        User userByUsername = userDao.findUserByUsername(username);
        //2.如果找到，验证密码是否正确(密码先加密后验证)
        if(userByUsername != null) {
            String encode = MD5Util.encode(password);
            if(encode.equals(userByUsername.getPassword())) {
                return userByUsername;
            }
        }
        return null;
    }

    @Override
    public User checkUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public User findByUsername(String savedUsername) {
        return userDao.findUserByUsername(savedUsername);
    }
}
