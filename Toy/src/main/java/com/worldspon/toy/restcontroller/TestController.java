package com.worldspon.toy.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

	@GetMapping("/test")
	public ModelAndView test() throws Exception {
		ModelAndView mav = new ModelAndView("test/test");
		
		return mav;
	}
}
