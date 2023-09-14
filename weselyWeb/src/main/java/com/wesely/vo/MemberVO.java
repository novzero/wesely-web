package com.wesely.vo;

import lombok.Data;

@Data
public class MemberVO {
	private String 	userid;
	private String 	password;
	private String 	username;
	private String 	nickname;
	private String 	phone;
	private String 	email;
	// 테이블에는 없지만 로그인 화면에서 사용하기 위해서 등록 
	private boolean saveID;
}
