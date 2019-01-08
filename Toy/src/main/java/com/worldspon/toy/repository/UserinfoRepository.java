package com.worldspon.toy.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Userinfo;

public interface UserinfoRepository extends JpaRepository<Userinfo, Long> {
	/**
	 * Userinfo 엔티티 클래스 JPA
	 * */
	
	
	/**
	 * 회원가입 아이디 중복 체크 쿼리
	 * args -------------------------------
	 * userid	| 사용자가 사용하려는 id 정보
	 * ------------------------------------
	 * return data ------------------------
	 * int		| COUNT 결과를 전달함 [0: 중복되지 않은 id, 1: 중복된 아이디]
	 * ------------------------------------
	 * */
	
	//NativeQuery Style
	/*
	@Query(value = "SELECT COUNT(u.uid) "
			+ "FROM USERINFO u "
			+ "WHERE u.userid LIKE :userid", nativeQuery = true)
	int checkId(@Param("userid")String userid);
	*/
	// 객체지향적 Method Style
	int countByUserid(String userid);
	
	
	
	/**
	 * 로그인 회원정보 체크 쿼리
	 * args -------------------------------
	 * userid	| 사용자가 로그인하려는 id 정보
	 * userpwd	| 사용자가 로그인하려는 password 정보
	 * ------------------------------------
	 * return data ------------------------
	 * Userinfo	| 회원정보를 엔티티 클래스 객체로 반환함
	 * ------------------------------------
	 * */
	
	 // NativeQuery Style
	 /*
	@Query(value = "SELECT u.username, u.useremail, u.userid "
			+ "FROM USERINFO u "
			+ "WHERE u.userid LIKE :userid "
			+ "AND u.userpwd LIKE :userpwd", nativeQuery = true)
	Map<String, Object> findUser(@Param("userid")String userid, @Param("userpwd")String userpwd);
	*/
	// 객체지향적 Method Style
	Userinfo findByUseridAndUserpwd(String userid, String userpwd);
}
