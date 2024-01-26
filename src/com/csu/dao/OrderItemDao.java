package com.csu.dao;

import com.csu.bean.OrderItem;

public interface OrderItemDao {

    /**
     * 保存订单项信息
     * @param orderItem
     */
    void addOrderItem(OrderItem orderItem);
}
