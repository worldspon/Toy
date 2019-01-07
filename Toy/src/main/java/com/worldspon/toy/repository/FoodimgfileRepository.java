package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Foodimgfile;

public interface FoodimgfileRepository extends JpaRepository<Foodimgfile, Long> {
	/**
	 * FoodimgfileRepository 엔티티 클래스 JPA
	 * */
}
