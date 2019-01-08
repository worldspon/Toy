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
	private HandlerInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> addPattern = new ArrayList<String>(); // 인터셉터 등록 패턴
		addPattern.add("/test/**");
		
		List<String> excludePattern = new ArrayList<String>();	// 인터셉터 예외 패턴
		excludePattern.add("/join/**");
		excludePattern.add("/login/**");
		
		registry.addInterceptor(interceptor)
			.addPathPatterns(addPattern)
			.excludePathPatterns(excludePattern);
	}
}
