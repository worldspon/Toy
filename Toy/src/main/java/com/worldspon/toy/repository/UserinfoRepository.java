package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Userinfo;

public interface UserinfoRepository extends JpaRepository<Userinfo, Long> {
	/**
	 * Userinfo 엔티티 클래스 JPA
	 * */

}
