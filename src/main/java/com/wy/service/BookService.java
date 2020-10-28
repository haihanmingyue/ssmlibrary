package com.wy.service;

import com.wy.pojo.Book;
import com.wy.pojo.User;

import java.util.List;
import java.util.Map;

public interface BookService {
    public List<Book> getBook(Map<String,Object> map);
}
