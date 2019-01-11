package com.worldspon.toy.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.worldspon.toy.dto.managerInfo.ManagerinfoRequestDto;
import com.worldspon.toy.dto.managerInfo.ManagerinfoResponseDto;
import com.worldspon.toy.entity.Managerinfo;
import com.worldspon.toy.repository.ManagerinfoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ManagerService {

	/**
	 * JPA repository 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private ManagerinfoRepository managerinfoRepo;
	
	/**
	 * 매니저 로그인 처리 서비스
	 * args -------------------------------
	 * ManagerInfoRequestDto	| 매니저 정보 객체
	 * HttpServletRequest		| 통신 요청 객체
	 * return data ------------------------
	 * map						| 매니저 정보 조회에 따른 처리 결과 값 [0: 로그인 실패, 1: 로그인 성공]
	 * ------------------------------------
	 */
	public HashMap<String, Object> loginProcess(ManagerinfoRequestDto dto, HttpServletRequest req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String managerid = dto.getManagerid();
		String managerpwd = dto.getManagerpwd();
		
		System.out.println(managerid + " / " + managerpwd);
		
		Managerinfo entity = managerinfoRepo.findByManageridAndManagerpwd(managerid, managerpwd);
		if (entity == null)
		{
			map.put("result", 0);
		}
		else
		{
			ManagerinfoResponseDto managerinfo = new ManagerinfoResponseDto();
			BeanUtils.copyProperties(entity, managerinfo);
			
			req.getSession().setAttribute("managerInfo", managerinfo);
			req.getSession().setMaxInactiveInterval(60 * 30);	// 매니저 정보 세션 유효 기간 30분
			map.put("result", 1);
		}
		
		return map;
	}
	
	/**
	 * 매니저 로그아웃 처리 서비스
	 * args -------------------------------
	 * HttpServletRequest	| 통신 요청 객체
	 * return data ------------------------
	 * msg					| 로그아웃 처리 결과 메시지 정보
	 * ------------------------------------
	 */
	public String logoutProcess(HttpServletRequest req) throws Exception {
		String msg = "";
		Object managerInfo = req.getSession().getAttribute("managerInfo");
		
		if (managerInfo == null || managerInfo.equals(""))
		{
			msg = "로그아웃 처리 중 에러가 발생하였습니다.";
		}
		else
		{
			req.getSession().invalidate();
			msg = "로그아웃이 처리되었습니다.";
		}
		
		return msg;
	}
}