package com.wy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{
	
	//在请求到达控制器方法前执行，返回true请求继续，返回false，中断请求
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Object loginUser = request.getSession().getAttribute("user");
		if(loginUser == null) {
			//没有登录
			String path = request.getContextPath();
			response.sendRedirect(path+"/denglu.html");
			return false;
		}
		return true;
		
	}

}
