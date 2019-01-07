package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Managerinfo;

public interface ManagerinfoRepository extends JpaRepository<Managerinfo, Long> {
	/**
	 * Managerinfo 엔티티 클래스 JPA
	 * */
}
