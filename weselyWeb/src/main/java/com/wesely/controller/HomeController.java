package com.wesely.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 메인컨트롤러
public class HomeController {
	
	// 시작 파일 지정
	@GetMapping(value = "/")
	public String home(Model model) {
		return "index";
	}
	
}