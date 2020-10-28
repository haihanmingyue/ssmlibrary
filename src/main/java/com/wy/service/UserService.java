package com.wy.service;

import com.wy.pojo.Book_Record;
import com.wy.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User getUser(String username);
    public User login(User user);
    public List<Book_Record> getHistory(Map<String,Object> map);
    public List<Book_Record> MyZuJie(Integer userid);
    public int MyZuJieCount(Integer userid);
    public int jiaoFei(Map<String,Object> map) throws Exception;
    public int changePhone(User user) throws Exception;
    public int huanShu(Map<String,Object> map) throws Exception;

    public int jieshu(List<Map<String,Object>> list)throws Exception;
}
