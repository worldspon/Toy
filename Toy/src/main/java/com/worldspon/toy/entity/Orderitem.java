package com.worldspon.toy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Orderitem extends BaseTimeEntity {

	/**
	 * Orderitem 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * orderitemid	| 오더 아이템 테이블 고유 아이디
	 * oid			| 오더 테이블 고유 아이디
	 * fid			| 신청 받은 음식 메뉴 테이블 고유 아이디
	 * foodname		| 신청 받은 음식 메뉴 이름
	 * stock		| 신청 받은 음식 수량
	 * foodprice	| 신청 받은 음식 지불 금액  
	 * -------------------------------------
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderitemid;
	@Column(nullable = false)
	private Long fid;
	@Column(nullable = false)
	private String foodname;
	@Column(nullable = false)
	private int stock;
	@Column(nullable = false)
	private int foodprice;
	/*
	 * optional		| 객체에 null을 허용하는 옵션. [true(Default), false]
	 */
	@ManyToOne(targetEntity = Orderlist.class, cascade = CascadeType.ALL)
	private Orderlist orderlist;
	
	@Builder
	public Orderitem(Long orderitemid, Long fid, String foodname, int stock, int foodprice, Orderlist orderlist) {
		this.orderitemid = orderitemid;
		this.fid = fid;
		this.foodname = foodname;
		this.stock = stock;
		this.foodprice = foodprice;
		if (orderlist != null)
		{
			this.orderlist = orderlist;
		}
	}
}
