package com.csu.service.impl;

import com.csu.bean.Book;
import com.csu.dao.BookDao;
import com.csu.dao.impl.BookDaoImpl;
import com.csu.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public List<Book> findAllBook() {
        return bookDao.findAllBook();
    }

    @Override
    public boolean saveBook(Book book) {
        return bookDao.saveBook(book);
    }

    @Override
    public boolean deleteBookById(String id) {
        return bookDao.deleteBookById(id);
    }

    @Override
    public Book findBookById(String id) {
        return bookDao.findBookById(id);
    }

    @Override
    public boolean updateBook(Book book) {
        return bookDao.updateBook(book);
    }
}
