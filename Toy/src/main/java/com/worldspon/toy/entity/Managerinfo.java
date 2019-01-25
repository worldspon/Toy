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
public class Managerinfo extends BaseTimeEntity {

	/**
	 * Managerinfo 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * mid			| 관리자 테이블 고유 아이디
	 * managername	| 관리자 이름 정보
	 * managerid	| 관리자 아이디 정보
	 * managerpwd	| 관리자 비밀번호 정보
	 * -------------------------------------
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mid;
	@Column(length = 100, nullable = false)
	private String managername;
	@Column(length = 100, nullable = false)
	private String managerid;
	@Column(length = 200, nullable = false)
	private String managerpwd;
	@Column()
	private String sessionid;
	
	@Builder
	public Managerinfo(Long mid, String managername, String managerid, String managerpwd, String sessionid) {
		this.mid = mid;
		this.managername = managername;
		this.managerid = managerid;
		this.managerpwd = managerpwd;
		this.sessionid = sessionid;
	}
}
