package com.csu.servlet.model;

import com.csu.bean.Book;
import com.csu.service.BookService;
import com.csu.service.impl.BookServiceImpl;
import com.csu.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "BookImageServlet", value = "/bookImage")
@MultipartConfig
public class BookImageServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    //去上传图片页面
    protected void toUploadBookImagePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("id");
        Book book = bookService.findBookById(bookId);
        request.setAttribute("book", book);
        this.processTemplate("manager/upload", request, response);
    }

    //上传文件
    protected void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求中获取文件部分
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        String filePath = "static/" + "uploads/" + fileName;
        String bookId = request.getParameter("id");
        System.out.println("bookId = " + bookId);
        Book book = bookService.findBookById(bookId);
        book.setImgPath(filePath);
        bookService.updateBook(book);//更新imagePath字段
        //写入图片文件目录
        filePart.write("D:\\Idea_java_projects\\web_experiment\\bookstore\\web\\static\\uploads\\" + fileName);
        //重定向到显示全部图书目录
        response.sendRedirect(request.getContextPath() + "/book?flag=findAllBook");
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
