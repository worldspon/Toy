package com.worldspon.toy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worldspon.toy.dto.fooditem.FooditemResponseDto;
import com.worldspon.toy.dto.orderitem.OrderitemRequestDto;
import com.worldspon.toy.dto.orderitem.OrderitemResponseDto;
import com.worldspon.toy.dto.orderlist.OrderlistRequestDto;
import com.worldspon.toy.entity.Fooditem;
import com.worldspon.toy.entity.Orderitem;
import com.worldspon.toy.entity.Orderlist;
import com.worldspon.toy.entity.Userinfo;
import com.worldspon.toy.repository.FooditemRepository;
import com.worldspon.toy.repository.OrderitemRepository;
import com.worldspon.toy.repository.OrderlistRepository;
import com.worldspon.toy.repository.UserinfoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderService.class);
	private UserinfoRepository userinfoRepo;
	private FooditemRepository fooditemRepo;
	private OrderlistRepository orderRepo;
	private OrderitemRepository orderitemRepo;
	
	
	/**
	 * 주문 가능한 상품 수량 체크
	 * args -------------------------------
	 * orderListReqDto		| 주문할 상품 아이템 정보
	 * ------------------------------------
	 * return data ------------------------
	 * check				| 상품 주문 수량 체크 결과 정보
	 * ------------------------------------
	 */
	@Transactional(readOnly = true)
	public boolean stockCheck(OrderlistRequestDto orderListReqDto) {
		boolean check = false;
		
		List<Orderitem> orderitemList = orderListReqDto.getOrderitem();
		List<Long> fids = new ArrayList<Long>();
		List<Integer> reqStock = new ArrayList<Integer>();
		
		// 주문한 상품들의 fid 얻어오기
		for (int i = 0; i < orderitemList.size(); i += 1)
		{
			fids.add(orderitemList.get(i).getFid());
			reqStock.add(orderitemList.get(i).getStock());
		}
		
		try
		{
			// 주문한 상품들 모두 조회
			List<Fooditem> fooditemEntity = fooditemRepo.findAllById(fids);
			
			for (int i = 0; i < fooditemEntity.size(); i += 1)
			{
				int maxStock = fooditemEntity.get(i).getStock();
				
				// 주문 신청한 상품 수량이 재고 수량보다 크면
				if (reqStock.get(i) > maxStock)
				{
					check = false;
					break;	// 하나의 상품이라도 재고 수량을 초과하면 주문 자체를 차단한다.
				}
				else
				{
					check = true;
				}
			}
		}
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
			// fids가 NULL인 경우
			check = false;
		}
		
		return check;
	}
	
	
	
	
	/**
	 * 주문 처리 서비스
	 * args -------------------------------
	 * orderList			| 쿠키 객체를 핸들링하기 위한 통신 객체
	 * req					| 통신 요청 객체
	 * res					| 통신 응답 객체
	 * ------------------------------------
	 * return data ------------------------
	 * map					| 처리 결과 정보
	 * ------------------------------------
	 */
	@Transactional // orderlist, orderitem 테이블 insert 중 문제 발생 시 fooditem, orderlist, oderitem 테이블 rollback
	public HashMap<String, Object> sendOrder(OrderlistRequestDto orderListReqDto, HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Object loginInfo = req.getSession().getAttribute("loginInfo");
		
		if (loginInfo == null)
		{
			// UserInterceptor에서 기본적으로 모든 비로그인 상태의 접속 시도를 차단함
			// 그러나 혹여나 생각하지 못한 예외 탈출구가 있을 경우를 생각하여 세션 정보 null 체크 처리를 함
			map.put("msg", "주문 서비스는 로그인 후 이용해주시기 바랍니다.");
		}
		else
		{
			try
			{
				// 주문자 식별 id, 주문자 이름 설정 (setter)
				// ====== START =====
				Userinfo userinfoEntity = userinfoRepo.findBySessionid(req.getSession().getId());
				orderListReqDto.setUid(userinfoEntity.getUid());
				orderListReqDto.setUsername(userinfoEntity.getUsername());
				// ====== END =====

				/*
				 * OrderList Entity와 OrderItem Entity가 서로
				 * One to Many - Many to One 관계형일 때 활성화
				// 자식 객체에 부모 정보 저장
				// ====== START =====
				List<Orderitem> orderitemList = new ArrayList<Orderitem>();
				
				for (int i = 0; i < orderListReqDto.getOrderitem().size(); i += 1)
				{
					OrderitemRequestDto orderitemReqDto = new OrderitemRequestDto();
					BeanUtils.copyProperties(orderListReqDto.getOrderitem().get(i), orderitemReqDto);
					orderitemReqDto.setOrderlist(orderListReqDto.toEntity());
					orderitemList.add(orderitemReqDto.toEntity());
					
				}
				// ====== END =====
				*/
				// 부모 객체에 자식 정보 저장
				// 227번 줄 orderitemRepo.saveAll(orderitemList) 코드를 통해 자식 테이블을 저장하지 않음 (양방향 관계 설정 일 때 사용)
				// orderListReqDto.setOrderitem(orderitemList);
				 
				
				if (!(orderListReqDto.getOrderitem().isEmpty()))
				{
					List<Orderitem> reqOrderItemEntity = orderListReqDto.getOrderitem();
					ArrayList<Long> reqFidList = new ArrayList<Long>();
					int[] reqStockList = null; // 신청받은 수량 정보들
					int reqTotalStock = orderListReqDto.getTotalstock(); // 모든 상품의 수량을 합산한 정보
					
					// 신청 상품들의 수량 구하기
					// ====== START =====
					for (int i = 0; i < reqOrderItemEntity.size(); i += 1)
					{
						// 상품 정보를 조회하기 위해 fid를 ArrayList에 담는다
						reqFidList.add(reqOrderItemEntity.get(i).getFid());
						
						if (i == 0)
						{
							reqStockList = new int[reqOrderItemEntity.size()];
						}
						reqStockList[i] = reqOrderItemEntity.get(i).getStock();
					}
					// ====== END =====
					
					// 사용자가 주문한 상품들의 수량을 체크하기 위해 정보를 조회함
					List<Fooditem> fooditemList = fooditemRepo.findAllById(reqFidList);
					List<Fooditem> fooditemEntityList = new ArrayList<Fooditem>();
					
					
					// 주문 신청한 수량만큼 차감시키기
					// ====== START =====
					for (int i = 0; i < fooditemList.size(); i += 1)
					{
						// 현재 구매 가능한 최대 수량
						int validStock = fooditemList.get(i).getStock();
						
						// 주문 신청한 수량이 구매 가능 수량을 초과한 경우
						if (reqStockList[i] > validStock)
						{
							String foodName = fooditemList.get(i).getFoodname();
							map.put("msg", "\'" + foodName + "\' 상품이 매진되어 더 이상 구매하실 수 없습니다.");
							
							break;
						}
						else
						{
							// DB에 저장된 상품 수량을 주문 신청된 수량만큼 차감하기
							Fooditem fooditemEntity = fooditemList.get(i);
							FooditemResponseDto fooditemResDto = new FooditemResponseDto();
							BeanUtils.copyProperties(fooditemEntity, fooditemResDto);
							
							// 잔여 수량
							int remainsStock = validStock - reqStockList[i];
							fooditemResDto.setStock(remainsStock);
							
							fooditemEntityList.add(fooditemResDto.toEntity());
						}
					}
					
					// fooditem 테이블 update
					// 테이블에서 상품 수량을 주문된 요청 수량 만큼 감소 시키기
					int updateProcVal = updateFoodStock(reqTotalStock, fooditemEntityList);
					// ====== END =====
					
					if (updateProcVal < 0)
					{
						map.put("msg", "주문 요청을 처리 중 문제가 발생했습니다.");
					}
					else
					{
						// orderList, orderitem 테이블 insert
						orderRepo.save(orderListReqDto.toEntity());	// OrderList 테이블과 OrderItem 테이블이 단방향 관계로 설정된 경우 사용 (only One to Many)
						// orderitemRepo.saveAll(orderitemList); // OrderList 테이블과 OrderItem 테이블이 양방향 관계로 설정된 경우 사용 (One to Many - Many to One)
						
						deleteCookies(reqFidList, req, res);
						
						map.put("msg", "주문이 접수되었습니다. \n 배송이 준비됩니다.");
					}
				}
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
				// SessionId, reqFidLis, orderitemList가 NULL 인 경우
				map.put("msg", "주문 요청을 처리 중 문제가 발생했습니다.");
			}
		}
		
		return map;
	}
	
	
	/**
	 * 상품 수량 감소 업데이트 서비스
	 * args -------------------------------
	 * reqTotalStock			| 모든 상품 수량을 합한 총 수량
	 * fooditemEntityList		| 업데이트 할 데이터를 담고 있는 상품 객체 리스트
	 * ------------------------------------
	 * return data ------------------------
	 * updateProcVal			| 처리 결과 정보 [-1: 예외 발생함, 0: 정상적으로 처리됨]
	 * ------------------------------------
	 */
	@Transactional // fooditem 테이블 업데이트 처리 중 문제 발생 시 rollback
	// 만약 이 기능을 호출하는 메소드에 @Transactional이 선언된 경우 이 옵션은 무시되고 그 옵션 설정을 따라감
	public int updateFoodStock(int reqTotalStock, List<Fooditem> fooditemEntityList) {
		int updateProcVal = 0;
		
		try 
		{
			// 기존 상품의 수량 변경 (기존 수량 - 주문 수량)
			// 주문 신청한 모 상품의 총 수량이 100개보다 적은 경우 
			if ((reqTotalStock % 100) < 0)
			{
				fooditemRepo.saveAll(fooditemEntityList);
			}
			else
			{
				List<Fooditem> pieceList = new ArrayList<Fooditem>();

				// ex) 2010개의 업데이트 해야 할 데이터가 있는 경우
				for (int i = 0; i < fooditemEntityList.size(); i += 1)
				{
					// 100개씩 담음
					pieceList.add(fooditemEntityList.get(i));
					
					// 100개씩 나눠서 업데이트
					if ( i > 0 && (i % 100) == 0)
					{
						fooditemRepo.saveAll(fooditemEntityList);
						
						// 100개씩 쌓인 데이터를 지우고 다시 100개 쌓기
						pieceList.clear();
					}
				}
				// 2000개의 데이터 처리 후 나머지 10개 처리
				fooditemRepo.saveAll(fooditemEntityList);
			}
		}
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
			// 업데이트 할 대상이 테이블에 없는 경우
			updateProcVal = -1;
		}
		
		return updateProcVal;
	}
	
	
	/**
	 * 주문 처리 된 상품 장바구니에서 삭제 처리 서비스
	 * args -------------------------------
	 * reqFidList		| 삭제할 쿠키의 정보가 담긴 배열 콜렉션
	 * req				| 쿠키 객체를 핸들링하기 위한 통신 객체
	 * res				| 쿠키 처리에 대한 응답 객체
	 * ------------------------------------
	 */
	public void deleteCookies(ArrayList<Long> reqFidList, HttpServletRequest req, HttpServletResponse res) {
		// 사용자 쿠키 데이터 삭제 (장바구니 상품 정보)
		// ====== START =====
		Cookie[] cookies = req.getCookies();
		
		if (cookies != null && cookies.length > 0)
		{
			for (int i = 0; i < cookies.length; i += 1)
			{
				if (!(cookies[i].getName().equals("JSESSIONID")))
				{
					// 장바구니에 저장되어있는 상품의 fid값 구하기
					Long fid = Long.parseLong((cookies[i].getValue().split("\\.")[0]).split(":")[1]);
					
					// 주문 신청한 상품은 장바구니에서 쿠키를 삭제함
					for (Long targetFid : reqFidList) 
					{
						if (fid == targetFid)
						{
							cookies[i].setValue("");
							cookies[i].setMaxAge(0);
							
							res.addCookie(cookies[i]);
							break;
						}
					}
				}
			}
			
			// 주문 요청하지 않은 장바구니 상품 정보(쿠키) 정렬
			ArrayList<Cookie> arrCookie = new ArrayList<Cookie>();
			int cookieNameIndex = 0;
			for (int i = 0; i < cookies.length; i += 1)
			{
				// [cart2, cart 5] -> [cart0, cart1]
				if (!(cookies[i].getName().equals("JSESSIONID")) && !(cookies[i].getValue().equals("")))
				{
					String newCookieName = "cart" + cookieNameIndex;
					
					// 삭제된 쿠키 이후에 존재하는 쿠키 값을 땡겨옴
					Cookie newCookie = new Cookie(newCookieName, cookies[i].getValue());
					newCookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키 유효 기간은 30일 (60초 * 60분 * 24시간 * 30일)
					
					arrCookie.add(newCookie);
					
					// 기존 쿠키들을 지움
					cookies[i].setValue("");
					cookies[i].setMaxAge(0);
					res.addCookie(cookies[i]);
					
					cookieNameIndex += 1;
				}
			}
			
			// 정렬된 상품 정보(쿠키) 생성
			for (int i = 0; i < arrCookie.size(); i += 1)
			{
				res.addCookie(arrCookie.get(i));
			}
		}
		// ====== END =====
	}
}
