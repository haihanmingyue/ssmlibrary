package com.wy.service;

import com.wy.mapper.BookDao;
import com.wy.mapper.UserDao;
import com.wy.pojo.Book;
import com.wy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    BookDao bookDao;


    @Override
    public List<Book> getBook(Map<String,Object> map) {
        return bookDao.getBook(map);
    }
}
