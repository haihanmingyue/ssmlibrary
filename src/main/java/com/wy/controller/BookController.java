package com.wy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.pojo.Book;
import com.wy.pojo.Book_Record;
import com.wy.pojo.User;
import com.wy.service.BookService;
import com.wy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;

    @RequestMapping("/bookindex")
    public ModelAndView userIndex(ModelAndView model){
        model.setViewName("/zujieindex.html");
        return model;
    }
    @RequestMapping("/getBook")
    @ResponseBody
    public PageInfo<Book> getBook(HttpServletRequest request){
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        String key = request.getParameter("key");
        String startPrice = request.getParameter("startPrice").trim();
        String endPrice = request.getParameter("endPrice").trim();
        String status = request.getParameter("status").trim();
        Map<String,Object> map = new HashMap<>();
        map.put("key",key);
        map.put("startPrice",startPrice);
        map.put("endPrice",endPrice);
        map.put("status",status);
        PageHelper.startPage(pageNum,7);
        Page<Book> page = (Page<Book>) bookService.getBook(map);
        return page.toPageInfo();
    }
    @RequestMapping(value = "/bookZuJie/addBook")
    public void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        User user1 = userService.getUser(user.getUsername());
         int i = userService.MyZuJieCount(user.getUserid());
         if(i!=0 || user1.getOwing_money()<0 ){
             response.getWriter().write("0");
         }else {
             Object o = request.getSession().getAttribute("addBookList");
             List<List<String>> addBook = (List<List<String>>) o;
             if(addBook == null){
                 addBook = new ArrayList<>();
             }
             if(addBook.size()<5){
                 String flag = request.getParameter("flag");
                 if(flag.equals("已被租借")){
                     response.getWriter().write("3");
                 }else {
                     boolean temp = true;
                     String bookId = request.getParameter("bookid");
                     for(List<String> list : addBook){
                         if(list.get(0).equals(bookId)){
                             temp = false;
                             break;
                         }
                     }
                     if(!temp){
                         response.getWriter().write("4");
                     }else {
                         String bookName = request.getParameter("bookname");
                         String author = request.getParameter("author");
                         String chubanshe = request.getParameter("chubanshe");
                         List<String> list = new ArrayList<>();
                         list.add(bookId);
                         list.add(bookName);
                         list.add(author);
                         list.add(chubanshe);
                         addBook.add(list);
                         request.getSession().setAttribute("addBookList",addBook);
                         response.getWriter().write("1");
                     }

                 }

             }else {
                 response.getWriter().write("2");
             }

         }

    }


    @RequestMapping(value = "/bookZuJie/getBookList",produces = "html/json;charset=UTF-8")
    @ResponseBody
    public String getBookList(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List<List<String>> lists = (List<List<String>>) request.getSession().getAttribute("addBookList");
        if(lists.size()>0){
            return JSON.toJSONString(lists);
        }
         else {
             return null;
        }
    }

    @RequestMapping(value = "/bookZuJie/deleteBookList")
    @ResponseBody
    public String deleteBookList(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String bookid = request.getParameter("bookid");
        List<List<String>> lists = (List<List<String>>) request.getSession().getAttribute("addBookList");
        boolean flag = false;
        int j = 0;
        for(int i=0;i<lists.size();i++){
            if(lists.get(i).get(0).equals(bookid)){
                j = i;
                flag =true;
                break;
            }
        }
        if(flag){
            lists.remove(j);
            request.getSession().setAttribute("addBookList",lists);
            return "1";
        }else {
            return "0";
        }
    }

    @RequestMapping("/bookZuJie/tiJ")
    @ResponseBody
    public String tJ(HttpServletRequest request){
        if(request.getSession().getAttribute("addBookList") != null){
          return "1" ;
        }else {
            return "0";
        }
    }


}
