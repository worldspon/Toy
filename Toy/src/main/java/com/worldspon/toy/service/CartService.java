package com.worldspon.toy.service;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.worldspon.toy.dto.fooditem.FooditemRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartService {

	private static Logger logger = LoggerFactory.getLogger(CartService.class);
	
	/**
	 * 장바구니 상품 추가 서비스
	 * args -------------------------------
	 * dto					| 쿠키 객체를 핸들링하기 위한 통신 객체
	 * req					| 통신 요청 객체
	 * res					| 통신 응답 객체
	 * ------------------------------------
	 * return data ------------------------
	 * foodlist				| 음식 메뉴 리스트 정보
	 * ------------------------------------
	 */
	
	public String saveCart(FooditemRequestDto dto,	HttpServletRequest req, HttpServletResponse res) {

		Cookie[] cookies = req.getCookies();
		Cookie cookie = null;
		String cookieName = "cart";
		String msg = "장바구니에 상품이 추가되었습니다.";
		
		
		if (cookies == null || cookies.length == 0)
		{
			// 쿠키를 생성한 적이 없다면 쿠키를 생성함
			cookie = new Cookie(cookieName, "");
		}
		else
		{
			// 기존 장바구니에 담겨있는 상품을 재 선택한 것인지 판단
			for (int i = 0; i < cookies.length; i += 1)
			{
				String getCookieName = cookies[i].getName();
				String[] tempValue = cookies[i].getValue().split("\\.");
				String[] cookiesValue = tempValue[0].split(":");
				Long cookiesFid = Long.parseLong(cookiesValue[1]);
				
				if (cookiesFid == dto.getFid())
				{
					// 기존 장바구니에 담겨있는 상품인 경우 쿠키를 수정함
					cookie = new Cookie(getCookieName, "");
					msg = "장바구니에 담긴 상품 정보가 수정되었습니다.";
					break;	// break로 반복문을 빠져나오지 않을 경우 문제 발생
				}
				else
				{
					// 새로 담을 상품인 경우 새로운 쿠키를 생성함
					cookieName = "cart" + cookies.length;
					cookie = new Cookie(cookieName, "");
				}
			}
		}
		
		// 사용자로부터 전달받은 상품 정보
		String fid = dto.getFid().toString();
		String foodprice = Integer.toString(dto.getFoodprice());
		String stock = Integer.toString(dto.getStock());
		
		// RFC 표준으로 인하여 세미콜론, 역슬래시, 슬래시 등의 특수문자를 쿠키 값으로 사용 불가능함
		// 마침표(.)를 기준점으로 fid, foodprice, stock 3개의 값으로 분류한다. 
		// 데이터 가공 시 split메소드를 이용해 마침표(.) 기준으로 잘라내면 핸들링 가능
		String cookieValue = "fid:" + fid + "." + "foodprice:" + foodprice + "." +  "stock:" + stock;
		
		cookie.setValue(cookieValue);
		cookie.setMaxAge(60 * 60 * 24 * 30);	// 쿠키 유효 기간은 30일 (60초 * 60분 * 24시간 * 30일)
		res.addCookie(cookie);
		
		return msg;
	}
}
