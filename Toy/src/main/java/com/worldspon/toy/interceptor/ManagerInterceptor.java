package com.worldspon.toy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ManagerInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 매니저 기능 전용 인터셉터
	 * */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try 
		{
			if (request.getSession().getAttribute("managerInfo") == null)
			{
				// 매니저 전용 기능을 URL로 타이핑으로 접근할 경우 매니저 로그인 페이지로 이동시킨다
				response.sendRedirect(request.getContextPath() + "/manager");
				//response.setHeader("msg", "로그인 후 이용해주시기 바랍니다.");
				String msg = "{\"msg\":\"로그인 후 이용해주시기 바랍니다. \\n로그인 화면으로 이동하시겠습니까?\",\"loginChk\":\"-1\"}";
				BasicJsonParser jsonParse = new BasicJsonParser();
				jsonParse.parseMap(msg);
				
				response.getWriter().write(msg);
				return false;
			}
			else
			{
				return true;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
