package com.worldspon.toy.dto.userinfo;

import com.worldspon.toy.entity.Userinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserinfoResponseDto {

	/**
	 * Userinfo 응답 전용 DTO 클래스
	 * -------------------------------------
	 * 컬럼명		| 설명
	 * -------------------------------------
	 * username		| 사용자 이름 정보
	 * useremail	| 사용자 이메일 정보
	 * userid		| 사용자 아이디 정보
	 * sessionid	| 세션 아이디 정보
	 * -------------------------------------
	 * */
	private Long uid;
	private String username;
	private String useremail;
	private String userid;
	private String userpwd;
	private String sessionid;
	
	public Userinfo toEntitiy() {
		return Userinfo.builder()
				.uid(uid)
				.username(username)
				.useremail(useremail)
				.userid(userid)
				.userpwd(userpwd)
				.sessionid(sessionid)
				.build();
	}
}
