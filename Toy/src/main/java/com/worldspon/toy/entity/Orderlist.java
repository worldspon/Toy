package com.worldspon.toy.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Orderlist extends BaseTimeEntity {

	/**
	 * Orderlist 테이블 엔티티 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * oid			| 오더 리스트 테이블 고유 아이디
	 * uid			| 사용자 테이블 고유 아이디
	 * username		| 사용자 이름
	 * status		| 오더 상태값	[0: 대기중, 1: 처리됨, 2: 거절됨, 3: 취소됨]
	 * totalStock	| 각각의 상품 수량을 합한 총 수량
	 * orderitem	| 오더 상품 정보
	 * -------------------------------------
	 * */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid;
	@Column(nullable = false)
	private Long uid;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private int status;
	@Column(nullable = false)
	private int totalstock;
	// 다대다, 다대일, 일대일, 일대다 등등 형상을 지정하는 어노테이션을 쓸 때 
	// mappedBy를 쓸 경우 JoinColumn 어노테이션과 충돌 나므로 둘 중 하나만 선언해야함
	/*
	 * targetEntity		| 관계를 맺을 Entity class 정의
	 * cascade			| 현 Entity의 변경에 대해 관계된 Entity도 변경 전략을 결정 (ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH)
	 * fetch			| 관계 Entity의 데이터 읽기 전략 [EAGER(default), LAZY] EAGER는 관계 Entity의 정보를 미리 읽음, LAZY는 실제 요청하는 순간 가져옴
	 * mappedBy			| 양방향 관계 설정 시 관계의 주체가 되는 쪽에서 정의  (단방향 시 기술하지 않음)
	 * orphanRemoval	| 관계 Entity에서 변경이 일어난 경우 DB 변경을 같이 함 (이 옵션은 DB Layer 레벨, cascade는 JPA Layer 레벨)
	 */
	@OneToMany(targetEntity = Orderitem.class, cascade = CascadeType.ALL, mappedBy = "orderlist")
	private List<Orderitem> orderitem;
	
	

	@Builder
	public Orderlist(Long oid, Long uid, String username, int status, int totalstock, List<Orderitem> orderitem) {
		this.oid = oid;
		this.uid = uid;
		this.username = username;
		this.status = status;
		this.totalstock = totalstock;
		if (orderitem != null)
		{
			this.orderitem = orderitem;
		}
	}
}
