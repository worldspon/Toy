package com.worldspon.toy.utils;

import java.util.UUID;

public class CommonUtils {

	public static String getRandomString() {
		// UUID = Universally Unique IDentifier - 일반적인 고유 식별자
		return UUID.randomUUID().toString().replace("-", ""); // 34e503ac-7b1e-44ea-8304-ae65eeb59ce3 => 34e503ac7b1e44ea8304ae65eeb59ce3
	}
}
