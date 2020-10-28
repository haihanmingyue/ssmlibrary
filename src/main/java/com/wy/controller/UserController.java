package com.wy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.Util.CalendarUtil;
import com.wy.pojo.Book;
import com.wy.pojo.Book_Record;
import com.wy.pojo.User;
import com.wy.service.BookService;
import com.wy.service.UserService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/userindex")
    public ModelAndView userIndex(ModelAndView model){
        model.setViewName("/userindex.html");
        return model;
    }

    @RequestMapping("/toZuJieHistory")
    public ModelAndView toZuJieHistory(ModelAndView model){
        model.setViewName("/zujiehistory.html");
        return model;
    }

    @RequestMapping("/toMyZuJie")
    public ModelAndView toMyZuJie(ModelAndView model){
        model.setViewName("/myzujie.html");
        return model;
    }

    @RequestMapping("/getUserMsg")
    @ResponseBody
    public User getUserMsg(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        return userService.getUser(user.getUsername());
    }

    @RequestMapping("/getMyZuJie")
    @ResponseBody
    public List<Book_Record> getMyZuJie(HttpServletRequest request){
        User u = (User) request.getSession().getAttribute("user");
        Integer userid = u.getUserid();
        return userService.MyZuJie(userid);
    }

    @RequestMapping("/getHistory")
    @ResponseBody
    public PageInfo<Book_Record> getHistory(HttpServletRequest request){
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        String key = request.getParameter("key");
        User u = (User) request.getSession().getAttribute("user");
        Integer userid = u.getUserid();
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("key",key);
        PageHelper.startPage(pageNum,7);
        Page<Book_Record> page =  (Page<Book_Record>) userService.getHistory(map);
        return page.toPageInfo();
    }


    @RequestMapping("/changePhone")
    public void xiuGaiPhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String phone = request.getParameter("phone");
        User user = (User) request.getSession().getAttribute("user");
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPhone(phone);
        int i = 0;
        try {
            i = userService.changePhone(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(i == 1){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    //还书
    @RequestMapping(value = "/haunshu")
    @ResponseBody
    @Transactional
    public String huanShu(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("user");
        Integer userid = u.getUserid();
        String zujiecode = request.getParameter("zujiecode");
        String bookid = request.getParameter("bookid");
        String money = request.getParameter("money");
        Double m = Double.parseDouble("-"+money);
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String strDate = sdf.format(date);
        map.put("recordid",zujiecode);
        map.put("upMoney",m);
        map.put("userid",userid);
        map.put("bookid",bookid);
        map.put("time",strDate);
        map.put("type",1);
        try {
         userService.huanShu(map);
         return "1";
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return "0";
        }
    }



    @RequestMapping(value = "/jieshu")
    @ResponseBody
    @Transactional
    public String jieShu(HttpServletRequest request){
        User u = (User) request.getSession().getAttribute("user");
        Object o = request.getSession().getAttribute("addBookList");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<List<String>> addBook = (List<List<String>>) o;
        List<Map<String,Object>> list = new ArrayList<>();
        Date date = new Date();
        String strDate = sdf.format(date);
        String s = CalendarUtil.dateToStr(date);
        for(List<String> list1 : addBook){
            Map<String,Object> map = new HashMap<>();
            map.put("bookid",list1.get(0));
            map.put("userid",u.getUserid());
            map.put("lendtime",strDate);
            map.put("expiretime",s);
            list.add(map);
        }
        try {
           int i = userService.jieshu(list);
           System.out.println(i);
           if(i== (1+list.size())){
               request.getSession().removeAttribute("addBookList");
               return "1";
           }else {
               TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
               return "0";
           }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return "0";
        }
    }

    @RequestMapping(value = "/jiaofei")
    @ResponseBody
    @Transactional
    public String jiaoFei(HttpServletRequest request) throws IOException {
       String money = request.getParameter("money");
       User u = (User) request.getSession().getAttribute("user");
       Map<String,Object> map = new HashMap<>();
       map.put("upMoney",money);
       map.put("userid",u.getUserid());
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = new Date();
       String strDate = sdf.format(date);
       map.put("time",strDate);
       map.put("type",2);
        int i = 0;
        try {
            i =  userService.jiaoFei(map);
            if(i!=2){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "0";
            }else {
                return "1";
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return "0";
        }

    }

}
