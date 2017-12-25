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

		String path=request.getServletPath();
		String parameter=request.getParameter("id");
		String redirectPath="/display/login";
		HttpSession session = request.getSession(true);
		
		User user = (User) session.getAttribute("CurrentUser");
		
		if(user == null) {

			if(path!=null || !path.equals("")){
				redirectPath=redirectPath+"?path="+path;
			}
			if(parameter!=null || !parameter.equals("")){
				if(redirectPath.contains("?")){
					redirectPath=redirectPath+"&parameter="+parameter;
				}else {
					redirectPath=redirectPath+"?parameter="+parameter;
				}
			}
			response.sendRedirect(request.getContextPath() + redirectPath);
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	
}
