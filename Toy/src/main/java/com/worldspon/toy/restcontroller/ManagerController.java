package com.worldspon.toy.restcontroller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.worldspon.toy.dto.managerInfo.ManagerinfoRequestDto;
import com.worldspon.toy.service.ManagerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ManagerController {

	/**
	 * JoinService 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private ManagerService managerService;
	
	
	
	
	
	/**
	 * 매니저 로그인 페이지 이동 메소드
	 * return data ------------------------
	 * mav					| 뷰 페이지 정보
	 * ------------------------------------
	 */ 
	@GetMapping("/manager/login")
	public ModelAndView openManagerLogin() throws Exception {
		ModelAndView mav = new ModelAndView("login/manager");
		
		return mav;
	}
	
	/**
	 * 매니저 로그인 처리 메소드
	 * args -------------------------------
	 * ManagerinfoRequestDto	| 매니저 정보 객체 { managerid: '', managerpwd: '' }
	 * HttpServletRequest		| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * map						| 회원정보 조회에 따른 처리 결과 값 [0: 로그인 실패, 1: 로그인 성공]
	 * ------------------------------------
	 */ 
	@PostMapping("/manager/login")
	public HashMap<String, Object> managerLogin(@RequestBody ManagerinfoRequestDto dto, HttpServletRequest req) throws Exception {
		System.out.println(dto.getManagername() + " / " + dto.getManagerid() + " / " + dto.getManagerpwd());
		HashMap<String, Object> map = managerService.loginProcess(dto, req);
		return map;
	}
	
	/**
	 * 매니저 로그아웃 처리 메소드
	 * args -------------------------------
	 * HttpServletRequest		| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * map						| 매니저 계정 로그아웃 처리에 대한 메시지
	 * ------------------------------------
	 */ 
	@GetMapping("/manager/logout")
	public HashMap<String, Object> managerLogout(HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String msg = managerService.logoutProcess(req);
		map.put("msg", msg);
		
		return map;
	}
	
	
	/**
	 * 매니저 상품 등록 페이지 이동 메소드
	 * return data ------------------------
	 * mav						| 상품 등록 뷰 페이지 이동 정보
	 * ------------------------------------
	 */ 
	@GetMapping("/manager/food_product")
	public ModelAndView openManagerProduct() throws Exception {
		ModelAndView mav = new ModelAndView("manager/manager_product");
		
		return mav;
	}
	
	/**
	 * 매니저 상품 판매 페이지 이동 메소드
	 * return data ------------------------
	 * mav						| 상품 판매 뷰 페이지 이동 정보
	 * ------------------------------------
	 */ 
	@GetMapping("/manager/food_sell")
	public ModelAndView openManagerSell() throws Exception {
		ModelAndView mav = new ModelAndView("manager/manager_sell");
		
		return mav;
	}
}
