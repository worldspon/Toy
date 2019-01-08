package com.worldspon.toy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class UserInterceptor extends HandlerInterceptorAdapter {  
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		try {
			// 로그인 상태가 아닌 경우 로그인 화면으로 이동처리
			if (request.getSession().getAttribute("loginInfo") == null) 
			{
				// 프로젝트의 Context Path 명을 반환하고 그 경로에 /login URL 요청을 보낸다.
				// LoginController로 요청이 전달됨
				response.sendRedirect(request.getContextPath() + "/login");
				return false;
			}
			else
			{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
}
