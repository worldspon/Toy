package com.worldspon.toy.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	@GetMapping("/hello")
	public String hello() throws Exception {
		return "Hello world!";
	}
	
	
	/*
	 * main.html에 대한 요청은 FooditemController에서 확인해주시기 바랍니다.
	 * 
	 * 
	@GetMapping("/")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}
	*/
}
