package com.wesely.vo;

import java.util.Date;

import lombok.Data;


@Data
public class StoreReviewVO {
	private int id;
	private int ref;
	private String nickname;
	private String userProfile;
	private int star;
	private String content;
	private Date regDate;
}
