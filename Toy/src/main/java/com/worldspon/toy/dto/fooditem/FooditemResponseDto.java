package com.worldspon.toy.dto.fooditem;

import com.worldspon.toy.entity.Fooditem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FooditemResponseDto {

	/**
	 * Fooditem 응답 전용 DTO 클래스
	 * -------------------------------------
	 * 컬럼명			| 설명
	 * -------------------------------------
	 * managername		| 매니저 이름 정보
	 * managerid		| 매니저 아이디 정보
	 * managerpwd		| 매니저 비밀번호 정보
	 * -------------------------------------
	 * */
	
	private String foodname;
	private int foodprice;
	private Long imgid;
	private int status;
	
	public Fooditem toEntity() {
		return Fooditem.builder()
				.foodname(foodname)
				.foodprice(foodprice)
				.imgid(imgid)
				.status(status)
				.build();
	}
}
