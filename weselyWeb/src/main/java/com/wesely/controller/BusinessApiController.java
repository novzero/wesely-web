package com.wesely.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesely.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BusinessApiController {

	@Autowired
	MemberService memberService;

	// 사업자정보 유효성 검사
	@RequestMapping(value = "/bnoCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String bnoCheck(@RequestParam String bno) throws Exception {
		log.info("{}의 bnoCheck 호출 : {}", this.getClass().getName(), bno);
		int count = memberService.bnoCheck(bno);
		log.info("{}의 bnoCheck 리턴 : {}", this.getClass().getName(), count);
		return count + "";
	}
}
