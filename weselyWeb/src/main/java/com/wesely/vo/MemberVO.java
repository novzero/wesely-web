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
	private String userid;
	private String password;
	private String uuid;
	private String username;
	private String nickname;
	private String email;
	private String phone;
	private String authority;

	private byte[] photo;
	private String fileName; // 원본이름
    
	// 사업자 등록번호 관련 필드
	private int ref;
	private String bNum1; // 사업자등록번호(맨앞3자리)
	private String bNum2; // 사업자등록번호(중간2자리)
	private String bNum3; // 사업자등록번호(맨뒤5자리)
	private String bno; // 사업자등록번호
	private String bname; // 대표자성명
	private String store; // 상호명

}
