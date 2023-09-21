package com.wesely.vo;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement

public class MemberVO {
	private int idx;
	private String 	userid;
	private String 	password;
	private String 	uuid;
	private String 	username;
	private String 	nickname;
	private String 	email;
	private String 	phone;
	private String 	authority;
	private boolean saveID;
}
