package com.worldspon.toy.restcontroller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.worldspon.toy.dto.userinfo.UserinfoRequestDto;
import com.worldspon.toy.service.JoinService;
import com.worldspon.toy.service.LoginService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class JoinController {
	
	/**
	 * JoinService, loginServce 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private JoinService joinService ;
	private LoginService loginService;
	
	
	/**
	 * 회원가입 페이지 이동 메소드
	 */ 
	@GetMapping("/join")
	public ModelAndView join(HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = loginService.loginChceck(req, "join/join");
		
		ModelAndView mav = new ModelAndView("");
		mav.setViewName(map.get("setViewName").toString());
		mav.addObject("msg", map.get("msg"));
		
		return mav;
	}
	/**
	 * 회원가입 - 아이디 중복체크 검사 메소드
	 * args -------------------------------
	 * userid	| 사용자로부터 입력받은 id 정보
	 * ------------------------------------
	 * return data ------------------------
	 * map		| 아이디 조회 카운트 정보 [0: 중복되지 않음, 1: 중복된 아이디]
	 * ------------------------------------
	 * */
	@PostMapping("/join/checkid")
	public HashMap<String, Object> checkId(@RequestParam(name="userid") String userid) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", joinService.checkId(userid));
		
		return map;
	}
	
	/**
	 * 회원가입 페이지 처리 메소드
	 * args -------------------------------
	 * UserinfoRequestDto	| 사용자 정보 DTO
	 * ------------------------------------
	 * return data ------------------------
	 * map					| 회원가입 처리 결과 메시지 정보
	 * ------------------------------------
	 */
	@PostMapping("/join")
	public HashMap<String, Object> joining(@RequestBody UserinfoRequestDto dto) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("msg", joinService.save(dto));
		return map;
	}
}
