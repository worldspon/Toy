package com.worldspon.toy.restcontroller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.worldspon.toy.dto.fooditem.FooditemRequestDto;
import com.worldspon.toy.service.CartService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CartController {
	private static Logger logger = LoggerFactory.getLogger(CartController.class);
	
	private CartService cartService;
	
	
	
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
		cartService.sortCookies(req, res);
		
		HashMap<String, Object> map = cartService.matchFooditem(req);
		
		ModelAndView mav = new ModelAndView("cart/cart");
		mav.addObject("map", map);
		
		return mav;
	}
	
	
	/**
	 * 장바구니 삭제된 상품 쿠키 삭제 요청 이동 메소드
	 * args -------------------------------
	 * dto				| 삭제할 쿠키의 정보가 담긴 객체 { fid: 1 }
	 * req				| 쿠키 객체를 핸들링하기 위한 통신 객체
	 * res				| 쿠키 처리에 대한 응답 객체
	 * return data ------------------------
	 * map				| 쿠키 삭제 처리 결과 정보
	 * ------------------------------------
	 */
	@PostMapping("/deleteCookie")
	public HashMap<String, Object> deleteCookie(@RequestBody FooditemRequestDto dto, HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> map = cartService.deleteCookie(dto, req, res);
		
		return map;
	}
}
