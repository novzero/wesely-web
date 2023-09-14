package com.wesely.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class CommunityVO {

	private String userid; // 아이디
	private String nickname; // 닉네임
	private String contents; //내용
	private String subject; // 제목
	private Date sysdate; // 작성날짜
}
