package com.worldspon.toy.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.worldspon.toy.dto.fooditem.FooditemRequestDto;
import com.worldspon.toy.entity.Fooditem;
import com.worldspon.toy.repository.FooditemRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartService {

	private static Logger logger = LoggerFactory.getLogger(CartService.class);
	private FooditemRepository fooditemRepo;
	
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
		String cookieName = "cart";
		Cookie cookie = new Cookie(cookieName + 0, "");
		String msg = "장바구니에 상품이 추가되었습니다.";
		
		
		// Session 쿠키 정보도 존해하지 않는 경우
		if (cookies == null || cookies.length == 0)
		{
			// 쿠키를 생성한 적이 없다면 쿠키를 생성함
			cookie = new Cookie(cookieName + 0, "");
		}
		else
		{
			// 기존 장바구니에 담겨있는 상품을 재 선택한 것인지 판단
			for (int i = 0; i < cookies.length; i += 1)
			{
				if (!(cookies[i].getName().equals("JSESSIONID")))
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
	
	
	
	/**
	 * 장바구니 상품 매칭 정보 조회 서비스
	 * args -------------------------------
	 * req						| 쿠키 객체를 핸들링하기 위한 통신 객체
	 * ------------------------------------
	 * return data ------------------------
	 * procException			| 상태 수정 처리 결과 정보 [0: 수정 처리 성공, 1: 수정 처리 중 문제 발생]
	 * ------------------------------------
	 */
	public HashMap<String, Object> matchFooditem(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();

		// 콜렉션 객체 생성
		List<Fooditem> foodList = new ArrayList<Fooditem>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (cookies == null || cookies.length == 0)
		{
			// 쿠키가 없을 때
		}
		else
		{
			ArrayList<Integer> cookiePrice = new ArrayList<Integer>();
			ArrayList<Integer> cookieStock = new ArrayList<Integer>();
			//int[] cookiePrice = new int[cookies.length];
			//int[] cookieStock = new int[cookies.length];
			ArrayList<Long> fidList = new ArrayList<Long>();

			for (int i = 0; i < cookies.length; i += 1)
			{
				// 쿠키 객체 중 로그인 정보를 담는 JSESSIONID는 처리를 건너뜀
				if (!(cookies[i].getName().equals("JSESSIONID")))
				{
					// fid:1.foodpirce:1000.stock:50 를 자른다.
					// Java의 split()에 인자는 정규표현식이므로 마침표를 문자 그대로 받아들이지 못한다.
					String[] tempValue = cookies[i].getValue().split("\\.");
					String[] cookieValue = tempValue[0].split(":");

					Long cookieFid = Long.parseLong(cookieValue[1]);
					
					fidList.add(cookieFid);
					
					// 상품 가격, 수량 가공 및 정렬
					cookiePrice.add(Integer.parseInt(tempValue[1].split(":")[1]));
					cookieStock.add(Integer.parseInt(tempValue[2].split(":")[1]));
				}
			}

			// 장바구니에 담긴 상품 조회
			foodList = fooditemRepo.findAllById(fidList);
			
			// 쿠키 정보를 fid 오름차순으로 정렬
			// JPA의 조회 쿼리의 Order By절은 기본적으로 ASC이므로 그에 맞는 정렬 처리를 해준다.
			for (int i = 0; i < fidList.size(); i += 1)
			{
				Cookie tempCookie = null;
				int tempCookiePrice = 0;
				int tempCookieStock = 0;
				
				for (int j = (i + 1); j < fidList.size(); j += 1)
				{
					for (int k = 0; k < cookies.length; k += 1)
					{
						if (!(cookies[k].getName().equals("JSESSIONID")))
						{
							if (fidList.get(i) > fidList.get(j))
							{
								tempCookie = cookies[j];
								cookies[j] = cookies[k];
								cookies[k] = tempCookie;
								
								tempCookiePrice = cookiePrice.get(i);
								cookiePrice.set(i, cookiePrice.get(j));
								cookiePrice.set(j, tempCookiePrice);
								
								tempCookieStock = cookieStock.get(i);
								cookieStock.set(i, cookieStock.get(j));
								cookieStock.set(j, tempCookieStock);
								// 수동 정렬처리를 해주지 않는 경우 
								// 장바구니 페이지에서 상품 수량, 상품 가격의 정보가 잘못된 순서로 출력된다.
							}
						}
					}
				}
			}
			
			map.put("foodList", foodList);
			map.put("price", cookiePrice);
			map.put("stock", cookieStock);
		}
		
		return map;
	}
	
	
	/**
	 * 장바구니 삭제된 상품 쿠키 삭제 요청 처리 서비스
	 * args -------------------------------
	 * dto				| 삭제할 쿠키의 정보가 담긴 객체 { fid: 1 }
	 * req				| 쿠키 객체를 핸들링하기 위한 통신 객체
	 * res				| 쿠키 처리에 대한 응답 객체
	 * return data ------------------------
	 * map				| 쿠키 삭제 처리 결과 정보
	 * ------------------------------------
	 */
	public HashMap<String, Object> deleteCookie(FooditemRequestDto dto, HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Long reqFid = dto.getFid();
		
		Cookie[] cookies = req.getCookies();
		
		if (cookies == null || cookies.length == 0)
		{
			// 삭제할 쿠키가 없을 때
			map.put("process", 0);
			map.put("msg", "상품을 삭제 중 문제가 발생하였습니다.");
		}
		else
		{
			Long[] cookieFid = new Long[cookies.length];
			
			for (int i = 0; i < cookies.length; i += 1) 
			{
				if (!(cookies[i].getName().equals("JSESSIONID")))
				{
					// fid:1.foodpirce:1000.stock:50 를 자른다.
					// Java의 split()에 인자는 정규표현식이므로 마침표를 문자 그대로 받아들이지 못한다.
					String[] tempValue = cookies[i].getValue().split("\\.");
					String[] cookieValue = tempValue[0].split(":");
					
					cookieFid[i] = Long.parseLong(cookieValue[1]);
					
					// 삭제를 요청한 상품 번호와 쿠키에 저장된 상품 번호가 같은 경우 쿠키를 삭제함
					// [cart, cart1, cart2, cart3] -> [cart, cart1, cart3]
					if (cookieFid[i] == reqFid)
					{
						cookies[i].setValue("");
						cookies[i].setMaxAge(0);
						
						res.addCookie(cookies[i]);

						// 쿠키 이름 재 설정 [cart, cart1, cart3] -> [cart, cart1, cart2]
						for (int j = (i + 1); j < cookies.length; j += 1)
						{
							if (!(cookies[j].getName().equals("JSESSIONID")))
							{
								// cart3 -> 3 
								String cartNameIndex = cookies[j].getName().substring(4);
								// cart3 -> cart2
								String newCookieName = "cart" + (Integer.parseInt(cartNameIndex) - 1) ;
								
								// 삭제된 쿠키 이후에 존재하는 쿠키 값을 땡겨옴
								Cookie cookie = new Cookie(newCookieName, cookies[j].getValue());
								cookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키 유효 기간은 30일 (60초 * 60분 * 24시간 * 30일)
								
								// [여러 개의 쿠키를 핸들링하는 방식으로 짜는 로직의 문제점]
								// 1. 특정 쿠키에 변화가 발생하면 쿠키를 정렬해줘야 하는 귀찮음이 발생 (Query의 Order by절과 연관된 경우)
								// 2. 특정 쿠키를 삭제하고 새로운 쿠키를 생성하는 패턴으로 기존의 쿠키를 정렬해야하는 경우 
								// MaxAge의 값을 수정해야하는데 기존 쿠키에 설정된 MaxAge값을 가져와서 핸들링할 수가 없음
								// 이는 브라우저가 서버에게 쿠키 이름과 값만 제공해주기 때문
								// 결론: 추후에 여러 개의 항목을 컨트롤할 때에는 하나의 쿠키에 객체 형식으로 데이터를 핸들링하는 것이 좋을 것 같음
								
								res.addCookie(cookie);
							}
						}
						
						map.put("process", 1);
						map.put("msg", "상품이 삭제되었습니다.");
					}
				}

			}
			// 모든 쿠키 이름을 재 설정한 후 찌꺼기 쿠키 제거
			int lastCookieIndex = cookies.length - 1; 
			cookies[lastCookieIndex].setValue("");
			cookies[lastCookieIndex].setMaxAge(0);
			
			res.addCookie(cookies[lastCookieIndex]);
		}
		
		return map;
	}
}
