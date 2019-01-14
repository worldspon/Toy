package com.worldspon.toy.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	@Qualifier(value = "userInterceptor")	// UserInteceptor 클래스 주입
	private HandlerInterceptor userinterceptor;
	//@Qualifier(value = "managerInterceptor")
	//private HandlerInterceptor managerinterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> addUserPattern = new ArrayList<String>(); // 인터셉터 등록 패턴
		addUserPattern.add("/test/**");
		
		List<String> excludeUserPattern = new ArrayList<String>();	// 인터셉터 예외 패턴
		excludeUserPattern.add("/join/**");		// 회원가입 서비스
		excludeUserPattern.add("/login/**");	// 로그인 서비스
		excludeUserPattern.add("/logout/**");	// 로그아웃 서비스
		excludeUserPattern.add("/manager/**");	// 매니저 전용 서비스
		excludeUserPattern.add("/cart/**");		// 장바구니 서비스
		
		registry.addInterceptor(userinterceptor)
			.addPathPatterns(addUserPattern)
			.excludePathPatterns(excludeUserPattern);
		/*
		 * registry.addInterceptor(managerinterceptor) .addPathPatterns("")
		 * .excludePathPatterns("");
		 */
	}
}
