package com.worldspon.toy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class UserInterceptor extends HandlerInterceptorAdapter {  
	
	/**
	 * 사용자 기능 전용 인터셉터
	 * */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		try 
		{
			// 로그인 상태가 아닌 경우 로그인 화면으로 이동처리
			if (request.getSession().getAttribute("loginInfo") == null) 
			{
				// 프로젝트의 Context Path 명을 반환하고 그 경로에 /login URL 요청을 보낸다.
				// LoginController로 요청이 전달됨
				// response.sendRedirect(request.getContextPath() + "/login/please");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				// 비동기 통신에 대한 응답 처리를 위한 메시지 JSON 파싱하기
				// loginChk [-1: 비 로그인 상태]
				String msg = "{\"msg\":\"로그인 후 이용해주시기 바랍니다. \\n로그인 화면으로 이동하시겠습니까?\",\"loginChk\":\"-1\"}";
				BasicJsonParser jsonParse = new BasicJsonParser();
				// JSON형식의 String을 MAP형태의 String으로 변환시켜줌
				jsonParse.parseMap(msg);

				//System.out.println(msg);
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
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
}
