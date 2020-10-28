package com.wy.service;

import com.wy.mapper.BookDao;
import com.wy.mapper.UserDao;
import com.wy.pojo.Book;
import com.wy.pojo.Book_Record;
import com.wy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;

    @Autowired
    BookDao bookDao;

    @Override
    public User getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public List<Book_Record> getHistory(Map<String,Object> map) {
        return userDao.getHistory(map);
    }

    @Override
    public List<Book_Record> MyZuJie(Integer userid) {
        return userDao.MyZuJie(userid);
    }

    @Override
    public int MyZuJieCount(Integer userid) {
        return userDao.MyZuJieCount(userid);
    }

    @Override
    public int jiaoFei(Map<String, Object> map) throws Exception{
        return userDao.jiaoFei(map) + userDao.insertRecord(map);
    }

    @Override
    @Transactional
    public int changePhone(User user) throws Exception {
        return userDao.changePhone(user);
    }

    @Override
    public int huanShu(Map<String, Object> map) throws Exception {
        int i = userDao.jiaoFei(map);
        int j = bookDao.upStatus(map);
        int k = userDao.updateRecord(map);
        int h = userDao.insertRecord(map);
        return i+j+k+h;
    }

    @Override
    public int jieshu(List<Map<String, Object>> list)throws Exception {
        int i =  bookDao.batchUpdate(list);
        int j = bookDao.insertBatch(list);
        return i+j;
    }
}
