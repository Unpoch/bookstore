package com.csu.dao.impl;

import com.csu.bean.Order;
import com.csu.dao.BaseDaoImpl;
import com.csu.dao.OrderDao;

import java.util.List;

public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) {
        String sql = "insert into t_order values(null,?,?,?,?,?,?)";
        this.update(sql, order.getOrderSequence(), order.getCreateTime(), order.getTotalCount(), order.getTotalAmount(), order.getOrderStatus(), order.getUserId());
    }

    @Override
    public Integer findIdBySequence(String sequence) {
        String sql = "select order_id from t_order where order_sequence=?";
        return (Integer) this.getValue(sql, sequence);
    }

    @Override
    public List<Order> findAllOrder(Integer userId) {
        String sql = "select order_id orderId,order_sequence orderSequence," +
                "create_time createTime,total_count totalCount,total_amount totalAmount," +
                "order_status orderStatus,user_id userId" +
                " from t_order where user_id=?";
        return this.getList(Order.class, sql, userId);
    }

    //查询所有订单项
    @Override
    public List<Order> findAll() {
        String sql = "select order_id orderId,order_sequence orderSequence," +
                "create_time createTime,total_count totalCount,total_amount totalAmount," +
                "order_status orderStatus,user_id userId" +
                " from t_order";
        return this.getList(Order.class, sql);
    }
}
