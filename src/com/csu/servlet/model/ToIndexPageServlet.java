package com.csu.servlet.model;

import com.csu.bean.Book;
import com.csu.service.BookService;
import com.csu.service.impl.BookServiceImpl;
import com.csu.servlet.base.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ToIndexPageServlet extends ViewBaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.查询到所有图书数据
        BookService bookService = new BookServiceImpl();
        List<Book> allBook = bookService.findAllBook();
        //2.放在请求域中
        request.setAttribute("books",allBook);
        this.processTemplate("index",request,response);
    }
}
