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
public class Orderitem {

	/**
	 * Orderitem 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * itemid		| 오더 아이템 테이블 고유 아이디
	 * oid			| 오더 테이블 고유 아이디
	 * fid			| 음식 메뉴 테이블 고유 아이디
	 * -------------------------------------
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemid;
	@Column(nullable = false)
	private Long oid;
	@Column(nullable = false)
	private Long fid;
	
	@Builder
	public Orderitem(Long fid) {
		this.fid = fid;
	}
}
