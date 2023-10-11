package com.wesely.service;

import org.springframework.stereotype.Service;

@Service
public interface BusinessService {
	
	// 사업자번호 중복검사
	int bnoCheck(String bno);
}