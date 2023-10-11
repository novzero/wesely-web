package com.wesely.vo;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement

public class BusinessDataVO {
	private String bNum1;		// 사업자등록번호(맨앞3자리)
	private String bNum2;		// 사업자등록번호(중간2자리)
	private String bNum3;		// 사업자등록번호(맨뒤5자리)
	private String bno = bNum1 + "-" + bNum2 + "-" + bNum3;		// 사업자등록번호
	private String bname;	// 대표자성명
	
}
