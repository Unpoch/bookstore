package com.csu.service;

import com.csu.bean.Cart;
import com.csu.bean.Order;
import com.csu.bean.User;

import java.util.List;

public interface OrderService {

    /**
     * 处理生成订单的业务
     * @param cart
     * @param user
     * @return
     */
    String createOrder(Cart cart, User user);

    /**
     * 根据用户id查询所有订单
     * @param userId
     * @return
     */
    List<Order> findAllOrder(Integer userId);

    List<Order> findAll();

}
