package com.worldspon.toy.restcontroller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.worldspon.toy.service.FooditemService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CartController {
	private static Logger logger = LoggerFactory.getLogger(CartController.class);
	
	private FooditemService fooditemService; 
	
	
	
	/**
	 * 장바구니 페이지 이동 메소드
	 * args -------------------------------
	 * req				| 쿠키 객체를 핸들링하기 위한 통신 객체
	 * return data ------------------------
	 * mav				| 쿠키에 담았던 상품 매칭 정보 및 페이지 이동 정보
	 * ------------------------------------
	 */
	@GetMapping("/cart")
	public ModelAndView openCart(HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> map = fooditemService.matchFooditem(req);
		
		ModelAndView mav = new ModelAndView("cart/cart");
		mav.addObject("map", map);
		
		return mav;
	}
}
