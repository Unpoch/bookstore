package com.csu.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/order")
public class LoginFilter implements Filter {


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //处于登录状态放行，不处于登录状态，让页面跳转至登录页面
        //是否处于登录状态：查看session中是否存在User对象
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Object user = request.getSession().getAttribute("user");
        if(user == null) {//不处于登录状态
            //重定向即可
            //让浏览器再发送一次请求到/user?flag=toLoginPage,访问UserServlet的toLoginPage方法，去到登录页面
            response.sendRedirect(request.getContextPath() + "/user?flag=toLoginPage");
        }else {//处于登录状态
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {

    }

}
