package com.worldspon.toy.dto.userinfo;

import com.worldspon.toy.entity.Userinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserinfoRequestDto {

	private String username;
	private String useremail;
	private String userid;
	private String userpwd;
	
	public Userinfo toEntity() {
		return Userinfo.builder()
				.username(username)
				.useremail(useremail)
				.userid(userid)
				.userpwd(userpwd)
				.build();
	}
}
