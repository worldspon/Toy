package com.worldspon.toy.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.worldspon.toy.dto.fooditem.FooditemRequestDto;
import com.worldspon.toy.dto.fooditem.FooditemResponseDto;
import com.worldspon.toy.entity.Fooditem;
import com.worldspon.toy.repository.FooditemRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FooditemService {

	/**
	 * JPA repository 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private FooditemRepository fooditemRepo;
	
	
	/**
	 * 음식 메뉴 목록 처리 서비스
	 * return data ------------------------
	 * foodlist				| 음식 메뉴 리스트 정보
	 * ------------------------------------
	 */
	public ArrayList<FooditemResponseDto> listFooditem() throws Exception {
		// 모든 음식 메뉴를 엔티티 클래스로 받아옴
		// List<Fooditem> entitylist = fooditemRepo.findAll();
		List<Fooditem> entitylist = fooditemRepo.findfood();
		
		ArrayList<FooditemResponseDto> foodlist = new ArrayList<FooditemResponseDto>();

		Iterator<Fooditem> entityiter = entitylist.iterator();

		// 엔티티 클래스의 정보들을 응답 객체 전용 dto로 복사함
		while (entityiter.hasNext())
		{
			FooditemResponseDto dto = new FooditemResponseDto();
			BeanUtils.copyProperties(entityiter.next(), dto);
			
			foodlist.add(dto);
		}
		
		return foodlist;
	}
	
	/**
	 * 음식 메뉴 등록 처리 서비스
	 * args -------------------------------
	 * dto				| 음식 메뉴 정보 객체
	 * return data ------------------------
	 * msg				| 음식 메뉴 등록 처리 결과 정보
	 * ------------------------------------
	 */
	public String addFooditem(FooditemRequestDto dto) throws Exception {
		String msg = (fooditemRepo.save(dto.toEntity()).getFid() > 0) ? "메뉴가 등록되었습니다." : "메뉴 등록 중 문제가 발생하였습니다.";
		
		return msg;
	}
	
	/**
	 * 음식 메뉴 등록 처리 서비스
	 * args -------------------------------
	 * dto				| 음식 메뉴 정보 객체
	 * return data ------------------------
	 * msg				| 음식 메뉴 등록 처리 결과 정보
	 * ------------------------------------
	 */
	public String modifyFooditem(FooditemRequestDto dto) throws Exception {
		
		return "";
	}
}
