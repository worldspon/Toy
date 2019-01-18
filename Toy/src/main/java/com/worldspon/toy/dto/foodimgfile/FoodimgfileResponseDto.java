package com.worldspon.toy.dto.foodimgfile;

import com.worldspon.toy.entity.Foodimgfile;
import com.worldspon.toy.entity.Fooditem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodimgfileResponseDto {

	/**
	 * Foodimgfile 응답 전용 DTO 클래스
	 * -------------------------------------
	 * 컬럼명			| 설명
	 * -------------------------------------
	 * imgfilename		| 이미지 파일 이름 정보	EX) 주니어 와퍼.png
	 * orgfilename		| 원본 파일 이름 정보	EX) junior_waper.png
	 * fooditem			| 음식 메뉴 정보
	 * -------------------------------------
	 * */
	Long imgid;
	private String imgfilename;
	private String orgfilename;
	private Fooditem fooditem;
	
	public Foodimgfile toEntity() {
		return Foodimgfile.builder()
				.imgid(imgid)
				.imgfilename(imgfilename)
				.orgfilename(orgfilename)
				.fooditem(fooditem)
				.build();
	}
}
