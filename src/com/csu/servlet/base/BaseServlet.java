package com.csu.servlet.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends ViewBaseServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获得flag的参数,进行判断
        String flag = request.getParameter("flag");
        Class c = this.getClass();//c就是子类的类信息
        try {
            Method method = c.getDeclaredMethod(flag, HttpServletRequest.class, HttpServletResponse.class);
            //调用这个方法
            method.setAccessible(true);//暴力访问
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);//出现异常，手动抛出运行时异常
        }
    }
}
