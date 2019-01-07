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
public class Userinfo {

	/**
	 * Userinfo 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * uid			| 사용자 테이블 고유 아이디
	 * username		| 사용자 이름 정보
	 * useremail	| 사용자 이메일 정보
	 * userid		| 사용자 아이디 정보
	 * userpwd		| 사용자 비밀번호 정보
	 * -------------------------------------
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	@Column(length = 100, nullable = false)
	private String username;
	@Column(length = 300, nullable = false)
	private String useremail;
	@Column(length = 100, nullable = false)
	private String userid;
	@Column(length = 200, nullable = false)
	private String userpwd;
	
	@Builder
	public Userinfo(String username, String useremail, String userid, String userpwd) {
		this.username = username;
		this.useremail = useremail;
		this.userid = userid;
		this.userpwd = userpwd;
	}
}
