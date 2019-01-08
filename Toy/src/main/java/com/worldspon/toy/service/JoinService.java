package com.worldspon.toy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worldspon.toy.dto.userinfo.UserinfoRequestDto;
import com.worldspon.toy.repository.UserinfoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JoinService {

	/**
	 * JPA repository 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private UserinfoRepository userinfoRepo;
	
	/**
	 * 사용자 정보 회원가입 처리
	 * args -------------------------------
	 * UserinfoRequestDto	| 사용자 정보 DTO
	 * ------------------------------------
	 * return data ------------------------
	 * msg					| 처리 결과 메시지 반환 값		
	 * ------------------------------------
	 */
	@Transactional
	public String save (UserinfoRequestDto dto) throws Exception{
		String msg = (userinfoRepo.save(dto.toEntity()).getUid() > 0) ? "회원가입이 처리되었습니다." : "회원가입에 실패했습니다.";
		return msg;
	}
	
	/**
	 * 사용 아이디 중복 체크 처리
	 * args -------------------------------
	 * UserinfoRequestDto	| 사용자 정보 DTO
	 * ------------------------------------
	 * return data ------------------------
	 * result				| 조회 결과 카운트 반환 값 [0: 중복되지 않음, n: 중복됨]		
	 * ------------------------------------
	 */
	@Transactional(readOnly = true)
	public int checkId(String userid) throws Exception {
		int result = 1;
		if (!(userid.equals("")) && userid != null)
		{
			result = userinfoRepo.countByUserid(userid);
		}
		return result;
	}
}
