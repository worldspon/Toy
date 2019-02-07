package com.worldspon.toy.restcontroller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.worldspon.toy.dto.orderlist.OrderlistRequestDto;
import com.worldspon.toy.service.OrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class OrderController {

	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	private OrderService orderService;
	

	
	
	/**
	 * 주문 처리 요청 메소드
	 * args -------------------------------
	 * orderList		| 사용자 주문 신청 정보
	 * req				| 통신 요청 객체 (로그인된 사용자의 정보를, 장바구니 상품 쿠키 정보)
	 * res				| 통신 응답 객체 (주문 처리 시 장바구니 상품 쿠키 정보 삭제)
	 * return data ------------------------
	 * map				| 주문 처리 결과 전달
	 * ------------------------------------
	 */
	@PostMapping("/order")
	public HashMap<String, Object> order(@RequestBody OrderlistRequestDto orderListReqDto, 
			HttpServletRequest req, HttpServletResponse res) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		boolean stockCheck = orderService.stockCheck(orderListReqDto);
		
		if (stockCheck == true)
		{
			map = orderService.sendOrder(orderListReqDto, req, res);
		}
		else
		{
			map.put("msg", "상품이 모두 매진되었습니다. 더 이상 구매하실 수 없습니다.");
		}
		
		return map;
	}
}
