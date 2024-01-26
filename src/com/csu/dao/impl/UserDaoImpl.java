package com.csu.dao.impl;

import com.csu.bean.User;
import com.csu.dao.BaseDaoImpl;
import com.csu.dao.UserDao;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {


    @Override
    public boolean addUser(User user) {
        String sql = "insert into users values(null,?,?,?)";
        int update = this.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
        return update > 0;
    }

    @Override
    public User findUserByUsername(String username) {
        String sql = "select * from users where username=?";
        return this.getBean(User.class,sql,username);
    }
}
