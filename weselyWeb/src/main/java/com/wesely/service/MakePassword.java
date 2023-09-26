package com.wesely.service;

import java.util.Random;

public class MakePassword {
	public static String makePassword(int length) {
		String newPassword ="";
		
		Random random = new Random();
		String spStr="~!@#$%^&&*";
		
		// 반복문으로 지정길이만큼의 임시비밀번호를 생성한다.
		for(int i=0;i<length;i++) {
			switch (random.nextInt(4)) {
			case 0:
				newPassword += random.nextInt(10); // 0~9까지의 난수
				break;
			case 1:
				newPassword += (char)('A' + random.nextInt(26));	// 대문자 알파벳
				break;
			case 2:
				newPassword += (char)('a' + random.nextInt(26));	// 소문자 알파벳
				break;
			case 3:
				newPassword += spStr.charAt(random.nextInt(spStr.length()));	// 특수문자
				break;
			}
		}
		return newPassword;
	}
}
