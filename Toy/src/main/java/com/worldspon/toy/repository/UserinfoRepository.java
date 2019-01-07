package com.worldspon.toy.repository;


import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	@Query(value = "SELECT COUNT(u.uid) "
			+ "FROM USERINFO u "
			+ "WHERE u.userid LIKE :userid", nativeQuery = true)
	int checkId(@Param("userid")String userid);
	
	/**
	 * 로그인 회원정보 체크 쿼리
	 * args -------------------------------
	 * userid	| 사용자가 로그인하려는 id 정보
	 * userpwd	| 사용자가 로그인하려는 password 정보
	 * ------------------------------------
	 * return data ------------------------
	 * Userinfo	| 회원정보를 객체로 반환함
	 * ------------------------------------
	 * */
	@Query(value = "SELECT u.username, u.useremail, u.userid "
			+ "FROM USERINFO u "
			+ "WHERE u.userid LIKE :userid "
			+ "AND u.userpwd LIKE :userpwd", nativeQuery = true)
	Map<String, Object> findUser(@Param("userid")String userid, @Param("userpwd")String userpwd);
}
