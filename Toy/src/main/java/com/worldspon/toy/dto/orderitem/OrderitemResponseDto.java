package com.worldspon.toy.dto.orderitem;

import com.worldspon.toy.entity.Orderitem;
import com.worldspon.toy.entity.Orderlist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderitemResponseDto {

	private Long orderitemid;
	private Long fid;
	private String foodname;
	private int stock;
	private int foodprice;
	//private Orderlist orderlist;
	
	
	@Builder
	public Orderitem toEntity() {
		return Orderitem.builder()
				.orderitemid(orderitemid)
				.fid(fid)
				.foodname(foodname)
				.stock(stock)
				.foodprice(foodprice)
				//.orderlist(orderlist)
				.build();
	}
}
