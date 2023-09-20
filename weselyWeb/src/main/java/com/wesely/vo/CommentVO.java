package com.wesely.vo;

import java.sql.Date;

import lombok.Data;

/*
	CREATE SEQUENCE comm_id_seq;
	CREATE TABLE comm(
		id NUMBER PRIMARY KEY,  -- 키필드
		REF NUMBER NOT NULL, -- 원본글 번호
		name varchar2(100) NOT NULL, -- 작성자
		password varchar2(100) NOT NULL, -- 암호
		content varchar2(2000) NOT NULL, -- 내용
		regdate timestamp DEFAULT sysdate, -- 작성일
		good NUMBER NOT NULL -- 좋아요 증가여부 
	);
 */
// 댓글쓰기
@Data
public class CommentVO {
	private int id;
	private int ref; // 원본글번호
	private String name; // 작성자
	private String content; // 내용
	private Date regdate; // 댓글작성날짜
	private int good;// 좋아요 증가여부
}
