package com.worldspon.toy.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CartController {

	@GetMapping("/cart")
	public ModelAndView openCart() throws Exception {
		ModelAndView mav = new ModelAndView("cart/cart");
		
		return mav;
	}
}
