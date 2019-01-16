package com.worldspon.toy.dto.fooditem;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FooditemRequestArrayDto {

	private ArrayList<FooditemRequestDto> foodlist;
}
