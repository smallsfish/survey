package com.hassdata.survey.interceptor;

import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AdminUserInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(true);

		Admin_User adminUser = (Admin_User) session.getAttribute("CurrentAdminUser");
		
		if(adminUser == null) {
			response.sendRedirect(request.getContextPath() + "/system/login");
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	
}
