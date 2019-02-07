package com.worldspon.toy.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * viewname				| view 페이지 위치 정보 로그인 컨트롤러와 회원가입 컨트롤러를 탈 때 뷰 네임 지정이 다르므로 인자로 직접 받아서 리턴함
	 * ------------------------------------
	 * return data ------------------------
	 * map					| 로그인 판단에 따른 뷰 지정 
	 * ------------------------------------
	 */
	public HashMap<String, Object> loginChceck(HttpServletRequest req, String viewname) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (req.getSession().getAttribute("loginInfo") != null)
		{
			map.put("msg", "이미 로그인된 상태입니다.");
			map.put("setViewName", "main");
		}
		else
		{
			map.put("setViewName", viewname);
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
	@Transactional
	public HashMap<String, Object> loginProcess(UserinfoRequestDto dto, HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String userid = dto.getUserid();
		String userpwd = dto.getUserpwd();
		
		try
		{
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
				req.getSession().setMaxInactiveInterval(30 * 60);	// 사용자 정보 세션 유효 기간 30분
				
				dto.setUsername(entity.getUsername());
				dto.setUseremail(entity.getUseremail());
				dto.setUid(entity.getUid());
				dto.setSessionid(req.getSession().getId());
				userinfoRepo.save(dto.toEntity());
				
				map.put("result", 1);
			}
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			// userid나 userpwd가 NULL인 경우
			map.put("result", 0);
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
	@Transactional
	public String logoutProcess(HttpServletRequest req) throws Exception {
		
		String msg = "";
		Object loginInfo = req.getSession().getAttribute("loginInfo");
		
		if (loginInfo == null || loginInfo.equals(""))
		{
			msg = "로그아웃 처리 중 에러가 발생하였습니다.";
		}
		else
		{
			try
			{
				// 세션ID 정보를 지울 사용자 정보를 조회
				Userinfo entity = userinfoRepo.findBySessionid(req.getSession().getId());
				UserinfoResponseDto userinfoResDto = new UserinfoResponseDto();
				BeanUtils.copyProperties(entity, userinfoResDto);
				userinfoResDto.setSessionid("");
				// Update로 세션ID를 지움
				userinfoRepo.save(userinfoResDto.toEntitiy());
				
				req.getSession().invalidate();
				
				msg = "로그아웃이 처리되었습니다.";
			}
			catch (IllegalArgumentException e) 
			{
				e.printStackTrace();
				// 조회할 조건절의 SessionId가 NULL인 경우
				msg = "로그아웃 처리 중 에러가 발생하였습니다.";
			}
		}
		
		return msg;
	}
}
