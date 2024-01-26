package com.csu.dao;

import com.csu.bean.Book;

import java.util.List;

public interface BookDao {

    List<Book> findAllBook();
    boolean saveBook(Book book);
    boolean deleteBookById(String id);
    Book findBookById(String id);
    boolean updateBook(Book book);
}
