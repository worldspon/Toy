package com.worldspon.toy.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.worldspon.toy.dto.userinfo.UserinfoRequestDto;
import com.worldspon.toy.dto.userinfo.UserinfoResponseDto;
import com.worldspon.toy.entity.Userinfo;
import com.worldspon.toy.repository.UserinfoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LoginService {

	/**
	 * JPA repository 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private UserinfoRepository userinfoRepo;
	
	/**
	 * 로그인 상태 체크 서비스
	 * args -------------------------------
	 * HttpServletRequest	| 통신 요청 객체
	 * ------------------------------------
	 * return data ------------------------
	 * map					| 로그인 판단에 따른 뷰 지정 
	 * ------------------------------------
	 */
	public HashMap<String, Object> loginChceck(HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (req.getSession().getAttribute("loginInfo") != null)
		{
			map.put("msg", "이미 로그인된 상태입니다.");
			map.put("setViewName", "main");
		}
		else
		{
			map.put("setViewName", "login/login");
		}
		
		return map;
	}
	
	/**
	 * 로그인 처리 서비스
	 * args -------------------------------
	 * UserinfoRequestDto	| 사용자 정보 객체
	 * HttpServletRequest	| 통신 요청 객체
	 * return data ------------------------
	 * map					| 회원정보 조회에 따른 처리 결과 값 [0: 로그인 실패, 1: 로그인 성공]
	 * ------------------------------------
	 */
	public HashMap<String, Object> loginProcess(UserinfoRequestDto dto, HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String userid = dto.getUserid();
		String userpwd = dto.getUserpwd();
		
		Userinfo entity = userinfoRepo.findByUseridAndUserpwd(userid, userpwd);
		if (entity == null)
		{
			map.put("result", 0);
		}
		else
		{
			// BeanUtils 스프링 프레임워크 객체를 이용하여 Entity 객체의 속성들을 DTO 객체의 속성에 복사함.
			UserinfoResponseDto userinfo = new UserinfoResponseDto();
			BeanUtils.copyProperties(entity, userinfo);
				
			req.getSession().setAttribute("loginInfo", userinfo);
			req.getSession().setMaxInactiveInterval(60 * 30);	// 사용자 정보 세션 유효 기간 30분
			map.put("result", 1);
		}
		
		return map;
	}
	/**
	 * 로그아웃 처리 서비스
	 * args -------------------------------
	 * HttpServletRequest	| 통신 요청 객체
	 * return data ------------------------
	 * msg					| 로그아웃 처리 결과 메시지 정보
	 * ------------------------------------
	 */
	public String logoutProcess(HttpServletRequest req) throws Exception {
		String msg = "";
		
		if (req.getSession().getAttribute("loginInfo") == null || req.getSession().getAttribute("loginInfo").equals(""))
		{
			msg = "로그아웃 처리 중 에러가 발생하였습니다.";
		}
		else
		{
			req.getSession().removeAttribute("loginInfo");
			msg = "로그아웃이 처리되었습니다.";
		}
		
		return msg;
	}
}
