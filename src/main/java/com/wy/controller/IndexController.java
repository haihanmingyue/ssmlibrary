package com.wy.controller;

import com.wy.pojo.User;
import com.wy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping
public class IndexController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/index")
    public String index(){
        return "/bookIndex.html";
    }

    @RequestMapping(value = "/toLogin")
    public String toLogin(HttpServletRequest request){
        return "/login.html";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUUser(HttpServletRequest request){
        User user = new User();
        if( request.getSession().getAttribute("user") != null) {
            return (User) request.getSession().getAttribute("user");
        }else {
            return user;
        }
    }


    @RequestMapping(value = "/login")
    public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User loginUser = userService.login(user);
        System.out.println(loginUser==null);
        if(loginUser!=null){
            System.out.println(loginUser.getStatus());
            if(loginUser.getStatus()==0){
                response.getWriter().write("2");
            }else {
                request.getSession().setAttribute("user",loginUser);
                response.getWriter().write("1");
            }
        }
    }

    @RequestMapping("/lgOut")
    public String lgOut(HttpServletRequest request){
        Object loginUser = request.getSession().getAttribute("user");
        request.getSession().invalidate();
        return "redirect:/index";
    }
}
