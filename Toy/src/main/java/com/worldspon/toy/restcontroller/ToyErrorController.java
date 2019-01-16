package com.worldspon.toy.restcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ToyErrorController implements ErrorController  {

	@RequestMapping(value = "/errorrr")
	public String error(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		String statusCode = String.valueOf(status);
		System.out.println(statusCode);
		System.out.println(statusCode instanceof String);
		System.out.println((HttpStatus.INTERNAL_SERVER_ERROR.toString()).split(" ")[0]);	// 400 Bad Request
		System.out.println((HttpStatus.INTERNAL_SERVER_ERROR.toString()).split(" ")[0] instanceof String);	// 400 Bad Request
		System.out.println(statusCode.equals((HttpStatus.INTERNAL_SERVER_ERROR.toString()).split(" ")[0]));	// 400 Bad Request
		
		if (statusCode.equalsIgnoreCase(HttpStatus.NOT_FOUND.toString().split(" ")[0]))
		{
			// 404 페이지 없음
			return "err/4xx";
		}
		else if (statusCode.equalsIgnoreCase(HttpStatus.BAD_REQUEST.toString().split(" ")[0]))
		{
			// 400 요청 실패
			return "err/4xx";
		}
		else if (statusCode.equalsIgnoreCase(HttpStatus.INTERNAL_SERVER_ERROR.toString().split(" ")[0]))
		{
			// 500 서버 내부 오류
			return "err/5xx";
		}
		else if (statusCode.equalsIgnoreCase(HttpStatus.BAD_GATEWAY.toString().split(" ")[0]))
		{
			// 502 게이트웨이를 통한 통신이 불안정하거나 서버에서 과부하가 발생
			return "err/5xx";
		}
		
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	
}
