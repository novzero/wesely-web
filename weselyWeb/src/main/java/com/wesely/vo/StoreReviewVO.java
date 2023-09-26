package com.wesely.vo;

import java.util.Date;

import lombok.Data;


@Data
public class StoreReviewVO {
	private int id; // 자동생성되는 리뷰의 고유id
	private int ref; // 리뷰를 남긴 시설 id
	private String nickname; // 유저 닉네임
	private String userProfile; // 유저 프로필사진
	private int star; // 별점
	private String content; // 리뷰내용
	private Date regdate; // 작성일
}
