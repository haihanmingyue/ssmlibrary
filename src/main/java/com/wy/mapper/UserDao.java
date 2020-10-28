package com.wy.mapper;

import com.wy.pojo.Book_Record;
import com.wy.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public User login(User user);
    public int MyZuJieCount(Integer userid);

    public int jiaoFei(Map<String,Object> map)throws Exception;
    public List<Book_Record> getHistory(Map<String,Object> map);
    public List<Book_Record> MyZuJie(Integer userid);
    public User getUser(String username);
    public int changePhone(User user) throws Exception;
    public int updateRecord(Map<String,Object> map);
    public int insertRecord(Map<String,Object> map);
}
