package com.csu.servlet.model;

import com.csu.bean.User;
import com.csu.service.UserService;
import com.csu.service.impl.UserServiceImpl;
import com.csu.servlet.base.BaseServlet;
import com.csu.utils.CommonResult;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    protected void toRegistPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("user/regist", request, response);
    }


    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证验证码是否正确
        String code = request.getParameter("code");
        Object kaptcha_session_key = request.getSession().getAttribute("KAPTCHA_SESSION_KEY");
        //1.获得请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (code.equals(kaptcha_session_key)) {

            //2.处理注册的业务(暂时不考虑用户名重复的问题)
            boolean b = userService.regist(user);
            //3.给响应，页面跳转(采用转发，因为转发页面有欢迎谁谁谁注册)
            if (b) {
                request.setAttribute("username", user.getUsername());
                this.processTemplate("user/regist_success", request, response);
            }
        } else {
            //注册失败，要做数据回显
            request.setAttribute("user", user);
            request.setAttribute("code", code);
            request.setAttribute("codeErrMsg", "验证码错误");
            this.processTemplate("user/regist", request, response);
        }
    }

    //toLoginPage
    protected void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processTemplate("user/login", request, response);
    }

    /*
    重构
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获得请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        // 2. 判断是否存在有效的Cookie
        Cookie[] cookies = request.getCookies();
        String savedUsername = getCookieValue(cookies, "username");
        System.out.println("savedUsername = " + savedUsername);
        // 3. 如果存在有效的Cookie并且和输入的用户名一致，则直接进行登录，不需要验证密码
        if (savedUsername != null) {
            if (savedUsername.equals(username)) {//是你的cookie
                // 从Cookie中获取到用户名，可以根据需要进行进一步的处理
                User user = userService.findByUsername(savedUsername);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    this.processTemplate("user/login_success", request, response);
                }
            } else {//有cookie，但不是你的cookie
                if (password == null || password.isEmpty()) {
                    request.setAttribute("errMsg", "请填写密码");
                    this.processTemplate("user/login", request, response);
                } else {//password != null
                    loginByUsernameAndPassword(request, response, username, password);
                }
            }
        } else { //没有cookie
            if (password == null || password.isEmpty()) {
                request.setAttribute("errMsg", "请填写密码");
                this.processTemplate("user/login", request, response);
            } else {//password != null
                loginByUsernameAndPassword(request, response, username, password);
            }
        }
    }

    private void loginByUsernameAndPassword(HttpServletRequest request, HttpServletResponse response,
                                            String username, String password) throws IOException {
        //4.调用业务层进行业务处理
        User user = userService.login(username, password);
        //5.给出页面响应
        if (user == null) {
            //登录失败：用户名的回显、错误信息的提示
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("errMsg", "用户名或密码错误");
            this.processTemplate("user/login", request, response);
        } else {
            // 设置用户名的Cookie
            Cookie usernameCookie = new Cookie("username", username);
            // 根据需要设置更多的Cookie属性，比如路径、域、最大存活时间等
            response.addCookie(usernameCookie);
            request.getSession().setAttribute("user", user);//放到会话域
            this.processTemplate("user/login_success", request, response);
        }
    }


    // protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //     //1.获得请求参数
    //     String username = request.getParameter("username");
    //     String password = request.getParameter("password");
    //     // 2. 判断是否存在有效的Cookie
    //     Cookie[] cookies = request.getCookies();
    //     String savedUsername = getCookieValue(cookies, "username");
    //     // 3. 如果存在有效的Cookie并且和输入的用户名一致，则直接进行登录，不需要验证密码
    //     if (savedUsername != null) {
    //         if (savedUsername.equals(username)) {
    //             // 从Cookie中获取到用户名，可以根据需要进行进一步的处理
    //             User user = userService.findByUsername(savedUsername);
    //             if (user != null) {
    //                 request.getSession().setAttribute("user", user);
    //                 this.processTemplate("user/login_success", request, response);
    //                 return;
    //             }
    //         } else {//有cookie，但不是你的cookie
    //             request.setAttribute("errMsg", "请填写密码");
    //             this.processTemplate("user/login", request, response);
    //         }
    //     } else if (password == null) {//这种情况是，没登陆过，没有cookie，但是你又不填写密码登录
    //         request.setAttribute("errMsg", "请填写密码");
    //         this.processTemplate("user/login", request, response);
    //     }
    //
    //     //4.调用业务层进行业务处理
    //     User user = userService.login(username, password);
    //     //3.给出页面响应
    //     if (user == null) {
    //         //登录失败：用户名的回显、错误信息的提示
    //         request.setAttribute("username", username);
    //         request.setAttribute("password", password);
    //         request.setAttribute("errMsg", "用户名或密码错误");
    //         this.processTemplate("user/login", request, response);
    //     } else {
    //         // 设置用户名的Cookie
    //         Cookie usernameCookie = new Cookie("username", username);
    //         // 根据需要设置更多的Cookie属性，比如路径、域、最大存活时间等
    //         response.addCookie(usernameCookie);
    //         request.getSession().setAttribute("user", user);//放到会话域
    //         this.processTemplate("user/login_success", request, response);
    //     }
    //
    // }

    private String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();//销毁session即可
        //如果要回首页，要去ToIndexPageServlet,获取数据展示，因此再发一次请求
        //ToIndexPageServlet的访问路径就是index.html
        response.sendRedirect(request.getContextPath() + "/index.html");
    }


    protected void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收请求参数
        String username = request.getParameter("username");
        //2.验证username是否存在
        User user = userService.checkUsername(username);
        //3.处理响应(使用CommonResult)
        CommonResult result = null;
        if (user == null) {//说明没有，说明ok
            result = CommonResult.ok();
        } else {
            result = CommonResult.error();
        }
        //变为json字符串返回
        String s = new Gson().toJson(result);
        System.out.println("s = " + s);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(s);
    }
}
