package com.wesely.vo;

import java.util.List;

import lombok.Data;

@Data
public class StoreVO {
	private int id;
	private String name; // 매장명
	private String address; // 매장주소
	private String opening; // 매장운영시간
	private String tel; // 매장번호
	private String description; // 매장설명
	private String hashTag; // 매장해쉬태그
	
	//추가
	private int reviewCount; //리뷰의 개수 -- 목록보기
	private List<StoreReviewVO> reviewList; // 리뷰목록 -- 내용보기
	
}
