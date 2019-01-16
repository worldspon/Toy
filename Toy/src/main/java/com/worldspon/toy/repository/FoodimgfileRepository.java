package com.worldspon.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldspon.toy.entity.Foodimgfile;

public interface FoodimgfileRepository extends JpaRepository<Foodimgfile, Long> {
	/**
	 * FoodimgfileRepository 엔티티 클래스 JPA
	 * */

	/**
	 * 특정 음식 이미지 정보 조회 쿼리
	 * args -------------------------------
	 * fid				| 음식 정보 고유 아이디 - from 부모 테이블
	 * ------------------------------------
	 * return data ------------------------
	 * Foodimgfile		| 엔티티 객체 정보
	 * ------------------------------------
	 * */
	public Foodimgfile findByFooditem_fid(Long fid);
	
	/**
	 * 음식 이미지 정보 삭제 쿼리
	 * args -------------------------------
	 * fid				| 음식 정보 고유 아이디 - from 부모 테이블
	 * ------------------------------------
	 * */
	public void deleteByFooditem_fid(Long fid);
}
