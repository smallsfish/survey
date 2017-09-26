package com.hassdata.survey.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hassdata.survey.po.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class UserInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(true);
		
		User user = (User) session.getAttribute("CurrentUser");
		
		if(user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	
}
