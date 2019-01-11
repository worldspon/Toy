package com.worldspon.toy.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	@Column(nullable = false)
	private Long imgid;
	*/
	/*
	 * cascade	| 영속성 전이 옵션 = 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장한다.
	 * */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fid")
	private List<Foodimgfile> foodimgfile;
	@Column(nullable = false)
	private int status;
	@Column(nullable = false)
	private int stock;
	
	@Builder
	public Fooditem(String foodname, int foodprice, List<Foodimgfile> foodimgfile, int status, int stock) {
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
	
	public void addFooditemfile(Foodimgfile foodimgfile) {
		this.foodimgfile.add(foodimgfile);
		foodimgfile.updateFooditem(this);
	}
}
