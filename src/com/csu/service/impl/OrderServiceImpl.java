package com.csu.service.impl;

import com.csu.bean.*;
import com.csu.dao.BookDao;
import com.csu.dao.OrderDao;
import com.csu.dao.OrderItemDao;
import com.csu.dao.impl.BookDaoImpl;
import com.csu.dao.impl.OrderDaoImpl;
import com.csu.dao.impl.OrderItemDaoImpl;
import com.csu.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, User user) {
        //1.创建订单信息并保存到数据库
        String orderSequence="CSU" + System.currentTimeMillis();//订单号
        Date date = new Date();
        //对当前系统时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);//字符串格式的当前系统时间
        Order order = new Order(null, orderSequence, format, cart.getTotalCount(), cart.getTotalAmount(), 0, user.getId());
        //调用dao层，将order的数据添加到数据库内
        orderDao.addOrder(order);
        //2.创建n个订单项信息并保存到数据库（批处理或者循环）
        //orderId就是刚刚新建的订单id，要根据订单编号查询(框架阶段，可以在新增的时候，返回自增的id的)
        Integer idBySequence = orderDao.findIdBySequence(orderSequence);
        Collection<CartItem> allCartItem = cart.getAllCartItem();
        //int i = 10/0;//存在异常，添加事物后，此时去结账，数据库应该毫无变化
        for (CartItem item : allCartItem) {
            //一个购物项对应一个订单项
            OrderItem orderItem = new OrderItem(null,item.getBook().getBookName(),item.getBook().getPrice(),item.getBook().getImgPath(),item.getCount(),item.getAmount(),idBySequence);
            //调用dao层将orderItem的数据保存到数据库
            orderItemDao.addOrderItem(orderItem);
            //3.对图书的库存和销量进行修改操作(暂不考虑库存不足的情况)
            //每一个订单项的生成都随之改变当前书的销量和库存
            Book book = item.getBook();//这就是需要修改信息的那本书
            book.setSales(book.getSales() + item.getCount());
            book.setStock(book.getStock() - item.getCount());
            bookDao.updateBook(book);//根据书的id修改其他信息
        }
        //将订单号返回
        return orderSequence;
    }

    @Override
    public List<Order> findAllOrder(Integer userId) {
        return orderDao.findAllOrder(userId);
    }

    //查询所有订单
    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }
}
