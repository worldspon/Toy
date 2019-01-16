package com.worldspon.toy.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Fooditem;

public interface FooditemRepository extends JpaRepository<Fooditem, Long> {
	/**
	 * Fooditem 엔티티 클래스 JPA
	 * */
	
	/**
	 * 판매 중인 모든 음식 메뉴 출력 쿼리
	 * args -------------------------------
	 * status				| 판매 / 비 판매 중 판단 정보
	 * ------------------------------------
	 * return data ------------------------
	 * List<Fooditem>		| 조회한 모든 데이터 목록을 리턴함
	 * ------------------------------------
	 * */
	public List<Fooditem> findByStatus(int status);
}
