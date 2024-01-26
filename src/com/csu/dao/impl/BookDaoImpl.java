package com.csu.dao.impl;

import com.csu.bean.Book;
import com.csu.dao.BaseDaoImpl;
import com.csu.dao.BookDao;

import java.util.List;

public class BookDaoImpl extends BaseDaoImpl implements BookDao {
    private String sql = " id bookId,title bookName,author,price,sales,stock,img_path imgPath ";

    @Override
    public List<Book> findAllBook() {
        String sql = "select " + this.sql + " from books";
        return this.getList(Book.class,sql);
    }

    @Override
    public boolean saveBook(Book book) {
        String sql = "insert into books values(null,?,?,?,?,?,?)";
        return this.update(sql,book.getBookName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath()) > 0;
    }

    @Override
    public boolean deleteBookById(String id) {
        String sql = "delete from books where id=?";
        return this.update(sql,id) > 0;
    }

    @Override
    public Book findBookById(String id) {
        String sql = "select " + this.sql + " from books where id=?";
        return this.getBean(Book.class,sql,id);
    }

    @Override
    public boolean updateBook(Book book) {
        String sql = "update books set title=?,author=?,price=?,sales=?,stock=?,img_path=? where id=?";
        return this.update(sql,book.getBookName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath(),book.getBookId())> 0;
    }


}
