package com.csu.dao.impl;

import com.csu.bean.OrderItem;
import com.csu.dao.BaseDaoImpl;
import com.csu.dao.OrderItemDao;

public class OrderItemDaoImpl extends BaseDaoImpl implements OrderItemDao {
    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item values(null,?,?,?,?,?,?)";
        this.update(sql,orderItem.getBookName(),orderItem.getPrice(),orderItem.getImgPath(),orderItem.getItemCount(),orderItem.getItemAmount(),orderItem.getOrderId());
    }
}
