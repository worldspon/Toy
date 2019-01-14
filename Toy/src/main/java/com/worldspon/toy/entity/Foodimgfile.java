package com.worldspon.toy.entity;

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
public class Foodimgfile extends BaseTimeEntity {
	/**
	 * Foodimgfile 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * imgid		| 음식 메뉴 이미지 테이블 고유 아이디
	 * imgfilename	| 음식 메뉴 이미지 파일의 오리지널 이름
	 * mid			| 음식 메뉴 이미지를 등록한 관리자의 고유 아이디
	 * -------------------------------------
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgid;
	@Column(length = 1000, nullable = false)
	private String imgfilename;
	@Column(length = 1000, nullable = false)
	private String orgfilename;
	@Column(nullable = false)
	private Long mid;
	@OneToOne
	private Fooditem fooditem;
	
	@Builder
	public Foodimgfile(String imgfilename, String orgfilename, Long mid) {
		this.imgfilename = imgfilename;
		this.orgfilename = orgfilename;
		this.mid = mid;
	}
	
}
