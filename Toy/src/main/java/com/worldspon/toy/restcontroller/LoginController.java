package com.worldspon.toy.restcontroller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.worldspon.toy.dto.userinfo.UserinfoRequestDto;
import com.worldspon.toy.service.LoginService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class LoginController {
	/**
	 * JoinService 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private LoginService loginService;
	
	/**
	 * 로그인 페이지 이동 메소드
	 * args -------------------------------
	 * HttpServletRequest	| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * mav					| 뷰 페이지 정보 및 로그인 상태 경고 메시지
	 * ------------------------------------
	 */ 
	@GetMapping("/login")
	public ModelAndView openLogin(HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = loginService.loginChceck(req);
		
		ModelAndView mav = new ModelAndView("");
		mav.setViewName(map.get("setViewName").toString());
		mav.addObject("msg", map.get("msg"));
		
		return mav;
	}
	
	/**
	 * 로그인 처리 메소드
	 * args -------------------------------
	 * UserinfoRequestDto	| 사용자 정보 객체
	 * HttpServletRequest	| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * map					| 회원정보 조회에 따른 처리 결과 값 [0: 로그인 실패, 1: 로그인 성공]
	 * ------------------------------------
	 */
	@PostMapping("/login")
	public HashMap<String, Object> login(@RequestBody UserinfoRequestDto dto, HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = loginService.loginProcess(dto, req);
		return map;
	}
	
	/**
	 * 로그아웃 처리 메소드
	 * args -------------------------------
	 * HttpServletRequest	| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * ModelAndView			| 로그아웃 처리 결과 메시지와 로그아웃 뷰 페이지 정보
	 * ------------------------------------
	 */
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest req) throws Exception {
		String msg = loginService.logoutProcess(req);
		ModelAndView mav = new ModelAndView("login/logout");
		mav.addObject("msg", msg);
		return mav;
	}
}
