package com.worldspon.toy.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;

//@Configuration
public class ErrorConfig {
	private String PATH = "/error";
	
//	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		// 404 페이지 없음
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, PATH));
		// 400 요청 실패
		factory.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, PATH));
		// 500 서버 내부 오류
		factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, PATH));
		// 502 게이트웨이를 통한 통신이 불안정하거나 서버에서 과부하가 발생
		factory.addErrorPages(new ErrorPage(HttpStatus.BAD_GATEWAY, PATH));
		
		
		return factory;
	}
	
}
