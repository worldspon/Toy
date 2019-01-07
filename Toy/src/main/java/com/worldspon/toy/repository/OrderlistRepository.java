package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Orderlist;

public interface OrderlistRepository extends JpaRepository<Orderlist, Long> {
	/**
	 * Orderlist 엔티티 클래스 JPA
	 * */
}
