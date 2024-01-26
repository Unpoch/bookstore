package com.csu.dao;

import com.csu.bean.Order;

import java.util.List;

public interface OrderDao {

    /**
     * 添加订单信息
     * @param order
     */
    void addOrder(Order order);

    /**
     *根据订单编号查询id值
     * @param sequence
     * @return
     */
    Integer findIdBySequence(String sequence);

    List<Order> findAllOrder(Integer userId);

    List<Order> findAll();
}
