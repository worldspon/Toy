package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Orderitem;

public interface OrderitemRepository extends JpaRepository<Orderitem, Long> {
	/**
	 * Orderitem 엔티티 클래스 JPA
	 * */
}
