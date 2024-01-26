package com.csu.servlet.model;

import com.csu.bean.Cart;
import com.csu.bean.Order;
import com.csu.bean.User;
import com.csu.service.OrderService;
import com.csu.service.impl.OrderServiceImpl;
import com.csu.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理结账请求
        //1.获得请求参数(获得结账相关的数据)
        //购物车信息
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        //当前登录人的信息(手动保持，处于登录状态(登录后再点击去结账))
        User user = (User) session.getAttribute("user");//UserServlet的login方法，将登录的User对象放入会话域
        //如果没有登录，user是null

        //2.调用业务层处理业务
        //订单的保存，订单项的保存 -> 业务层
        String orderSequence = orderService.createOrder(cart, user);
        //把购物车清空一下
        session.removeAttribute("cart");
        //3.给响应
        //需要将订单号，放在请求域，转发到网页，通过Thymeleaf渲染
        request.setAttribute("orderSequence", orderSequence);
        this.processTemplate("cart/checkout", request, response);
    }


    protected void showOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获得当前登录人的对象(根据当前登录人的id值去数据库查询) --> 先登录再看我的订单
        User user = (User) request.getSession().getAttribute("user");
        //2.调用业务层处理业务
        List<Order> allOrder = orderService.findAllOrder(user.getId());
        //3.给响应
        request.setAttribute("orders", allOrder);
        this.processTemplate("order/order", request, response);
    }

    //去订单管理页面
    protected void toOrdersManagerPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询所有订单项
        List<Order> allOrders = orderService.findAll();
        //放入请求域
        request.setAttribute("orders", allOrders);
        this.processTemplate("manager/order_manager", request, response);
    }

}
