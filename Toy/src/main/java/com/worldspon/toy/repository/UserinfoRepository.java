package com.worldspon.toy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.worldspon.toy.entity.Userinfo;

public interface UserinfoRepository extends JpaRepository<Userinfo, Long> {
	/**
	 * Userinfo 엔티티 클래스 JPA
	 * */
	
	@Query(value = "SELECT COUNT(u.uid) "
			+ "FROM USERINFO u "
			+ "WHERE u.userid LIKE :userid", nativeQuery = true)
	int checkId(@Param("userid")String userid);
}
