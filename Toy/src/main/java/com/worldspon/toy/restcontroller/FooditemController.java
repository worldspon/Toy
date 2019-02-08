package com.worldspon.toy.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.worldspon.toy.dto.fooditem.FooditemRequestDto;
import com.worldspon.toy.dto.fooditem.FooditemResponseDto;
import com.worldspon.toy.service.CartService;
import com.worldspon.toy.service.FooditemService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class FooditemController {
	/**
	 * FooditemService 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private FooditemService fooditemService;
	private CartService cartService;

	
	
	
	/**
	 * 메인 홈페이지 / 음식 메뉴 목록 처리 서비스
	 * return data ------------------------
	 * mav						| 뷰 페이지 정보, 음식 리스트 정보
	 * ------------------------------------
	 */
	@GetMapping("/")
	public ModelAndView main() throws Exception {
		ArrayList<FooditemResponseDto> list = fooditemService.listFooditem();
		
		ModelAndView mav = new ModelAndView("main");
		mav.addObject("foodlist", list);

		return mav;
	}
	
	
	
	
	
	/**
	 * 장바구니 상품 추가 메소드
	 * args -------------------------------
	 * dto			| 장바구니에 등록할 음식 메뉴 정보 {fid:1.foodprice:5000.stock:50}
	 * req			| 통신 요청 객체
	 * res			| 통신 응답 객체
	 * return data ------------------------
	 * map			| 장바구니 처리 결과 메시지 정보
	 * ------------------------------------
	 */
	@PostMapping("/saveCart")
	public HashMap<String, Object> saveCart(@RequestBody FooditemRequestDto dto, 
			HttpServletRequest req, HttpServletResponse res) {
		String msg = cartService.saveCart(dto, req, res);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("msg", msg);
		
		return map;
	}
	
}
