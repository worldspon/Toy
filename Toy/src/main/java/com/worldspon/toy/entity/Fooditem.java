package com.worldspon.toy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	 * foodname		| 음식 메뉴 가격
	 * imgid		| 불러올 음식 메뉴 이미지 고유 아이디
	 * -------------------------------------
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fid;
	@Column(length = 300, nullable = false)
	private String foodname;
	@Column(nullable = false)
	private int foodprice;
	@Column(nullable = false)
	private Long imgid;
	@Column(nullable = false)
	private int status;
	@Column(nullable = false)
	private int stock;
	
	@Builder
	public Fooditem(String foodname, int foodprice, Long imgid, int status, int stock) {
		this.foodname = foodname;
		this.foodprice = foodprice;
		this.imgid = imgid;
		this.status = status;
		this.stock = stock;
	}
}
