package com.csu.servlet.model;

import com.csu.bean.Book;
import com.csu.bean.Cart;
import com.csu.bean.CartItem;
import com.csu.service.BookService;
import com.csu.service.impl.BookServiceImpl;
import com.csu.servlet.base.BaseServlet;
import com.csu.utils.CommonResult;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CartServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理加入购物车的请求
        //1.获得请求参数
        //获得图书的id值
        String id = request.getParameter("id");
        System.out.println("id = " + id);
        //获得购物车的对象(判断有无购物车对象 -> 去session中获取，看是否能取到)
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {//session中没有cart对象
            cart = new Cart();
            //对象存储在session中
            session.setAttribute("cart", cart);
        }
        //程序运行到此处，cart就是购物车对象


        //2.处理业务(不连接数据库，因此没有 cart service)
        //根据图书id，查到图书的信息
        Book book = bookService.findBookById(id);
        //将图书对象传递到Cart类中，判断map集合中是否有图书的信息(第一次加入购物车还是第二次)
        cart.addCart(book);
        //3.给出响应
        //响应内容添加一个总数量(获得总数量
        Integer totalCount = cart.getTotalCount();
        CommonResult ok = CommonResult.ok().setResultData(totalCount);
        String s = new Gson().toJson(ok);
        System.out.println("s = " + s);//flag:true,resultData:?
        response.getWriter().write(s);
    }


    protected void toCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将页面跳转，设置到cart.html
        this.processTemplate("cart/cart", request, response);
    }


    protected void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得到购物车的所有数据，然后响应给js
        //1.n个购物想 2.总数量 3.总金额
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //拿到map集合所有value值，为什么直接返回map集合？因为拿到value值可以在前端遍历
        Collection<CartItem> allCartItem = cart.getAllCartItem();
        Integer totalCount = cart.getTotalCount();
        Double totalAmount = cart.getTotalAmount();
        //将这些数据，变为json字符串返回给js
        List<Object> list = new ArrayList<>();
        list.add(allCartItem);
        list.add(totalCount);
        list.add(totalAmount);
        CommonResult commonResult = CommonResult.ok().setResultData(list);
        String s = new Gson().toJson(commonResult);
        System.out.println("s = " + s);
        //{flag:true,resultData:[[{购物项},{购物项},{购物项}],5,500]}
        response.getWriter().write(s);
    }


    protected void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("cart");
        this.processTemplate("cart/cart", request, response);
    }


    protected void deleteCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        //调用cart中的方法删除购物项
        cart.deleteCartItem(Integer.parseInt(id));
        //后台的数据删除成功了，前台怎么办，因为是异步请求，前台是不刷新的
        //那么可以再调用showCart方法
        showCart(request, response);
    }

    protected void addCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获得请求参数
        String id = request.getParameter("id");
        //2.获得购物车对象
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        //3.调用cart对象中的方法，对当前id对应购物项进行数量+1操作
        cart.addCount(Integer.parseInt(id));
        //4.后台的数据已经刷新完毕，前台的怎么办
        //用showCart对数据进行刷新，展示在页面上
        showCart(request, response);
    }


    protected void subtractCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.subtractCount(Integer.parseInt(id));
        showCart(request, response);
    }


    protected void changeCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String newCount = request.getParameter("newCount");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.changeCount(Integer.parseInt(id),Integer.parseInt(newCount));
        showCart(request,response);
    }

}
