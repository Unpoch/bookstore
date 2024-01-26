package com.csu.filter;

import com.csu.utils.JDBCTools;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "TransactionFilter", urlPatterns = "/*")
public class TransactionFilter implements Filter {


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.获得数据库连接
        Connection connection = JDBCTools.getConnection();
        try {
            //2.设置手动提交
            connection.setAutoCommit(false);
            //3.放行
            chain.doFilter(req, resp);
            //4. 如果运行到此处，没有异常产生，提交事物即可
            connection.commit();
        } catch (Exception e) {//异常捕获类型一定是最大的
            e.printStackTrace();
            //5.如果程序走到这，说明有异常产生
            try {
                connection.rollback();//有一场则回滚
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //6.不管有无一场产生，释放资源是必须要执行的
            JDBCTools.releaseConnection();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
