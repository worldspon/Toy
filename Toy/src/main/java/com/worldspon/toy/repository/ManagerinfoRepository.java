package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Managerinfo;

public interface ManagerinfoRepository extends JpaRepository<Managerinfo, Long> {
	/**
	 * Managerinfo 엔티티 클래스 JPA
	 * */
	
	/**
	 * 매니저 회원정보 체크 쿼리
	 * args -------------------------------
	 * managerid	| 매니저가 로그인하려는 id 정보
	 * managerpwd	| 매니저가 로그인하려는 password 정보
	 * ------------------------------------
	 * return data ------------------------
	 * Managerinfo		| 매니저정보를 엔티티 클래스 객체로 반환함
	 * ------------------------------------
	 * */
	Managerinfo findByManageridAndManagerpwd(String managerid, String managerpwd);
	
	Managerinfo findBySessionid(String sessionid);
}
