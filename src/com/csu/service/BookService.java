package com.csu.service;

import com.csu.bean.Book;

import java.util.List;

public interface BookService {

    /**
     * 查询所有图书
     * @return
     */
    List<Book> findAllBook();

    /**
     * 添加图书
     * @param book
     * @return
     */
    boolean saveBook(Book book);

    /**
     * 根据id删除图书
     * @param id
     * @return
     */
    boolean deleteBookById(String id);

    /**
     * 根据id查找图书信息
     * @param id
     * @return
     */
    Book findBookById(String id);

    /**
     * 更新图书信息
     * @param book
     * @return
     */
    boolean updateBook(Book book);
}
