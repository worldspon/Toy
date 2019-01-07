package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Fooditem;

public interface FooditemRepository extends JpaRepository<Fooditem, Long> {
	/**
	 * Fooditem 엔티티 클래스 JPA
	 * */
}
