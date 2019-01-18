package com.worldspon.toy.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.worldspon.toy.dto.fooditem.FooditemRequestArrayDto;
import com.worldspon.toy.dto.fooditem.FooditemRequestDto;
import com.worldspon.toy.dto.fooditem.FooditemResponseDto;
import com.worldspon.toy.dto.managerInfo.ManagerinfoRequestDto;
import com.worldspon.toy.service.FooditemService;
import com.worldspon.toy.service.ManagerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ManagerController {
	
	/* 예외처리 */
	/*
	@ExceptionHandler(value = Exception.class)
	public String nfeHandler(Exception e) {
		System.out.println("error!!!");
		//ModelAndView mav = new ModelAndView("errorrr");
		//return mav;
		return e.getMessage();
	}
	*/
	

	/**
	 * ManagerService 주입
	 * FooditemService 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private ManagerService managerService;
	private FooditemService fooditemService;
	
	
	/**
	 * 매니저 로그인 페이지 리다이렉션 메소드
	 * return data ------------------------
	 * mav					| 매니저 로그인 페이지 요청 리다이렉션 정보
	 * ------------------------------------
	 * */
	@GetMapping("/manager")
	public ModelAndView openManagerHome() throws Exception {
		ModelAndView mav = new ModelAndView("redirect:manager/login");
		
		return mav;
	}
	
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
	public ModelAndView openManagerFoodProduct() throws Exception {
		ArrayList<FooditemResponseDto> list = fooditemService.listAllFooditem();
		
		ModelAndView mav = new ModelAndView("manager/manager_product");
		mav.addObject("foodlist", list);
		
		return mav;
	}
	
	/**
	 * 매니저 상품 등록 처리 메소드
	 * args -------------------------------
	 * dto						| 새로 추가할 음식 메뉴 정보
	 * HttpServletRequest		| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * mav						| 처리 결과 메시지 및 뷰 페이지 정보
	 * ------------------------------------
	 */
	@PostMapping("/manager/food_product")
	public ModelAndView managerFoodProduct(@ModelAttribute FooditemRequestArrayDto dtolist, HttpServletRequest req) throws Exception {
		/*
		 * 참고 ----------------------------------
		 * @ModelAttribute가 아닌 @RequestBody 어노테이션을 사용할 경우 파일 객체를 핸들링할 수 없는 사이드 이펙트가 발생함
		 * */ 
		
		String msg = fooditemService.addFooditem(dtolist, req);
		
		ModelAndView mav = new ModelAndView("manager/manager_alert");
		mav.addObject("msg", msg);
		
		return mav;
	}
	
	
	
	
	
	
	
	/**
	 * 매니저 상품 수정 페이지 이동 메소드
	 * args -------------------------------
	 * fid						| 수정할 상품의 아이디 정보
	 * ------------------------------------
	 * return data ------------------------
	 * mav						| 상품 등록 뷰 페이지 이동 정보
	 * ------------------------------------
	 */
	@GetMapping("/manager/food_modify")
	public ModelAndView openManagerFoodModify(@RequestParam(name="fid", defaultValue="0", required=true) Long fid) {
		HashMap<String, Object> dtoMap = fooditemService.getFooditem(fid);
		
		ModelAndView mav = new ModelAndView("manager/manager_mod");
		mav.addObject("fooditemMap", dtoMap);
		
		return mav;
	}
	
	/**
	 * 매니저 상품 수정 처리 메소드
	 * args -------------------------------
	 * fooditemDto				| 수정된 상품의 정보 객체
	 * HttpServletRequest		| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * mav						| 상품 등록 뷰 페이지 이동 정보
	 * ------------------------------------
	 */
	@PostMapping("/manager/food_modify")
	public ModelAndView managerFoodModify(@ModelAttribute FooditemRequestDto fooditemDto, HttpServletRequest req) {
		String msg = fooditemService.modifyFooditem(fooditemDto, req);
		
		ModelAndView mav = new ModelAndView("manager/manager_alert");
		mav.addObject("msg", msg);
		
		return mav;
	}
	
	
	
	
	
	

	/**
	 * 매니저 상품 삭제 처리 메소드
	 * args -------------------------------
	 * fidMap					| 삭제할 상품 고유 아이디 정보
	 * ------------------------------------
	 * return data ------------------------
	 * delProcException			| 삭제 처리 결과 정보 [0: 삭제 처리 성공, 1: 삭제 처리 중 문제 발생]
	 * ------------------------------------
	 */
	@PostMapping("/manager/food_delete")
	public int managerFoodDelete(@RequestBody HashMap<String, Long[]> fidMap) {
		int delProcException = fooditemService.deleteFooditem(fidMap);
		
		return delProcException;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 매니저 상품 판매 상태 수정 페이지 이동 메소드
	 * return data ------------------------
	 * mav						| 상품 판매 뷰 페이지 이동 정보
	 * ------------------------------------
	 */ 
	@GetMapping("/manager/food_sell")
	public ModelAndView openManagerSell() throws Exception {
		ArrayList<FooditemResponseDto> list = fooditemService.listAllFooditem();
		
		ModelAndView mav = new ModelAndView("manager/manager_sell");
		mav.addObject("foodList", list);
		return mav;
	}
	
	
	/**
	 * 매니저 상품 판매 상태 수정 처리 메소드
	 * args -------------------------------
	 * map						| 상태 수정할 상품 고유 아이디, 변경할 상태 정보 {findStatus : 0 or 1, status : 0 or 1, fid : [1, 2, 3, 4, ...]}
	 * ------------------------------------
	 * return data ------------------------
	 * procException			| 상태 수정 처리 결과 정보 [0: 수정 처리 성공, 1: 수정 처리 중 문제 발생]
	 * ------------------------------------
	 */
	@PostMapping("/manager/food_sell")
	public int managerSell(@RequestBody HashMap<String, Object> map) {
		int procException = fooditemService.changeFooditemStatus(map);
		
		return procException;
	}
}
