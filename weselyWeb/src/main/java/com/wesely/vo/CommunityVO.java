package com.wesely.vo;

import java.sql.Date;
import java.util.List;

import lombok.Data;

/*CREATE SEQUENCE community_id_seq;
CREATE TABLE community(
	id NUMBER PRIMARY KEY, -- 키필드
	userid varchar2(50) NOT NULL, -- 아이디
	nickname varchar2(100) NOT NULL, -- 작성자
	password varchar2(100) NOT NULL, -- 비번
	contents varchar2(300) NOT NULL, -- 글 내용
	subject varchar2(100) NOT NULL, -- 글 제목
	regDate timestamp DEFAULT sysdate , -- 작성일
	readCount NUMBER DEFAULT 0 -- 조회수
);
 * */
@Data
public class CommunityVO {

	private int id;
	private String userid; // 아이디
	private String nickname; // 닉네임
	private String password; // 비번
	private String contents; //내용
	private Date regDate; // 작성날짜
	private int readCount; // 조회수
	private int commentCount; // 댓글 개수 - 목록보기
	private List<CommentVO> commentList; // 댓글목록 - 내용보기
	private Date sysdate; // 작성날짜
	private List<CommunityImgVO>imgList;// 이미지 
}
