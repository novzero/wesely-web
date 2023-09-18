package com.wesely.vo;

import lombok.Data;

@Data
public class CommVO {
	private int p;  // 현재페이지
	private int s;  // 페이지당 글수
	private int b;  // 하단 페이지 개수
	private int id; // 글번호
	private int mode; // 조회수 증가여부
}
