package com.worldspon.toy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Foodimgfile extends BaseTimeEntity {
	/**
	 * Foodimgfile 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * imgid		| 음식 메뉴 이미지 테이블 고유 아이디
	 * imgfilename	| 음식 메뉴 이미지 파일의 오리지널 이름
	 * fooditem		| 부모 테이블 정보
	 * -------------------------------------
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgid;
	@Column(length = 1000, nullable = false)
	private String imgfilename;
	@Column(length = 1000, nullable = false)
	private String orgfilename;
	@OneToOne
	@JoinColumn(name = "fooditem_fid")
	private Fooditem fooditem;
	
	@Builder
	public Foodimgfile(Long imgid, String imgfilename, String orgfilename, Fooditem fooditem) {
		this.imgid = imgid;
		this.imgfilename = imgfilename;
		this.orgfilename = orgfilename;
		if (fooditem != null)
		{
			this.fooditem = fooditem;
		}
	}
}
