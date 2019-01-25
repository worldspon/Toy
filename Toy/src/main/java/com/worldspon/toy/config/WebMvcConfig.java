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
	@Autowired
	@Qualifier(value = "managerInterceptor")
	private HandlerInterceptor managerinterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* 사용자 패턴 */
		List<String> addUserPattern = new ArrayList<String>(); // 인터셉터 등록 패턴
		addUserPattern.add("/test/**");					// 테스트 서비스 추후 삭제 요망
		addUserPattern.add("/logout");					// 로그아웃 서비스
		addUserPattern.add("/order/**");				// 주문 서비스
		
		List<String> excludeUserPattern = new ArrayList<String>();	// 인터셉터 예외 패턴
		excludeUserPattern.add("/join/**");			// 회원가입 서비스
		excludeUserPattern.add("/login/**");		// 로그인 서비스
		excludeUserPattern.add("/manager/login");	// 매니저 전용 서비스
		excludeUserPattern.add("/cart/**");			// 장바구니 서비스
		
		
		/* 매니저 패턴 */
		List<String> addManagerPattern = new ArrayList<String>(); 		// 인터셉터 등록 패턴
		addManagerPattern.add("/manager/food_product");		// 상품 추가 서비스 
		addManagerPattern.add("/manager/food_modify");		// 상품 수정 서비스
		addManagerPattern.add("/manager/food_delete");		// 상품 삭제 서비스
		addManagerPattern.add("/manager/food_sell");		// 상품 판매 상태 변경 서비스
		addManagerPattern.add("/manager/logout");			// 로그아웃 서비스
		
		List<String> excludeManagerPattern = new ArrayList<String>();	// 인터셉터 예외 패턴
		excludeManagerPattern.add("/join/**");		// 회원가입 서비스
		excludeManagerPattern.add("/login/**");		// 로그인 서비스
		excludeManagerPattern.add("/manager/login");// 매니저 전용 서비스
		excludeManagerPattern.add("/cart/**");		// 장바구니 서비스
		
		// 사용자 패턴 인터셉터 등록
		registry.addInterceptor(userinterceptor)
			.addPathPatterns(addUserPattern)
			.excludePathPatterns(excludeUserPattern);
		
		// 매니저 패턴 인터셉터 등록
		registry.addInterceptor(managerinterceptor) 
			.addPathPatterns(addManagerPattern)
			.excludePathPatterns(excludeManagerPattern);
	}
}
