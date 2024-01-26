package com.csu.dao;

import com.csu.bean.User;

public interface UserDao {
    /**
     * 功能：将user对象的数据保存到数据库
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 功能：根据用户名查找用户对象
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
