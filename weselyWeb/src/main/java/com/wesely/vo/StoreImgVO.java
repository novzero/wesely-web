package com.wesely.vo;

import lombok.Data;

@Data
public class StoreImgVO {
	private int id;  // 키필드
	private int ref; // 원본글 번호
	private String uuid; // 중복제거처리 위한 키
	private String fileName; // 원본이름
	private String contentType; // 파일 종류
	private int iorder; // 이미지 순서
}
