package com.worldspon.toy.dto.orderlist;

import java.util.List;

import com.worldspon.toy.entity.Orderitem;
import com.worldspon.toy.entity.Orderlist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderlistRequestDto {

	/**
	 * Orderlist DTO 클래스
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
	 * -------------------------------------
	 * */
	
	private Long oid;
	private Long uid;
	private String username;
	private int status;
	private int totalstock;
	private List<Orderitem> orderitem;
	
	public Orderlist toEntity() {
		return Orderlist.builder()
				.oid(oid)
				.uid(uid)
				.username(username)
				.status(status)
				.totalstock(totalstock)
				.orderitem(orderitem)
				.build();
	}
}
