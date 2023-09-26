package com.wesely.vo;

import lombok.Data;

@Data
public class GoodVO {

	private int id; // 키필드
	private String ref; //원본글 번호
	private String nickname; // 유저 별명
	private String whether; // 좋아요 여부
}
