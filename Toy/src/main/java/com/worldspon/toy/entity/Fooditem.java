package com.worldspon.toy.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Fooditem extends BaseTimeEntity {

	/**
	 * Fooditem 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * fid			| 음식 메뉴 테이블 고유 아이디
	 * foodname		| 음식 메뉴 이름
	 * foodprice	| 음식 메뉴 가격
	 * imgid		| 불러올 음식 메뉴 이미지 고유 아이디
	 * status		| 음식 메뉴 판매상태
	 * stock		| 음식 메뉴 재고 수량
	 * -------------------------------------
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fid;
	@Column(length = 300, nullable = false)
	private String foodname;
	@Column(nullable = false)
	private int foodprice;
	/*
	 * cascade	| 영속성 전이 옵션 = 부모 엔티티를 insert, update, delete등 수정할 때 연관된 자식 엔티티도 함께 작업을 처리한다.
	 * */
	@OneToOne(mappedBy="fooditem", cascade = CascadeType.ALL)
	private Foodimgfile foodimgfile;
	
	@Column(nullable = false)
	private int status;
	@Column(nullable = false)
	private int stock;
	
	@Builder
	public Fooditem(Long fid, String foodname, int foodprice, Foodimgfile foodimgfile, int status, int stock) {
		this.fid = fid;
		this.foodname = foodname;
		this.foodprice = foodprice;
		if (foodimgfile != null)
		{
			this.foodimgfile = foodimgfile;
		}
		//this.imgid = imgid;
		this.status = status;
		this.stock = stock;
	}
}
