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
public class Orderlist extends BaseTimeEntity {

	/**
	 * Orderlist 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * oid			| 오더 리스트 테이블 고유 아이디
	 * uid			| 사용자 테이블 고유 아이디
	 * status		| 오더 상태값	[0: 대기중, 1: 처리됨, 2: 거절됨, 3: 취소됨]
	 * -------------------------------------
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long oid;
	@Column(nullable = false)
	private Long uid;
	@Column(nullable = false)
	private int status;

	@Builder
	public Orderlist(Long uid, int status) {
		this.uid = uid;
		this.status = status;
	}
}
