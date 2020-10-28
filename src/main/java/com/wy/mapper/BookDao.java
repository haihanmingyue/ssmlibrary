package com.wy.mapper;

import com.wy.pojo.Book;
import com.wy.pojo.User;

import java.util.List;
import java.util.Map;

public interface BookDao {

    public List<Book> getBook(Map<String,Object> map);
    public int upStatus(Map<String,Object> map);
    public int batchUpdate(List<Map<String,Object>> list);
    public int insertBatch(List<Map<String,Object>> list);
}
