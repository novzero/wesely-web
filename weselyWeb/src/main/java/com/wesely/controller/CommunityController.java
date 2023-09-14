package com.wesely.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wesely.vo.CommVO;

@Controller
@RequestMapping("/community")
public class CommunityController {

	// 커뮤니티 목록보기
	@RequestMapping(value = { "/", "/list" })
	public String getList(@ModelAttribute CommVO cv, Model model) {

		return "/comm/community";
	}

	// 커뮤니티 저장하기
	// 입력폼
	@GetMapping(value = "/insert")
	public String insert(@ModelAttribute CommVO cv, Model model) {
		
		return "comm/createPost";
	}
	
	// 커뮤니티 상세보기
	@GetMapping(value = "/view")
	public String view(@ModelAttribute CommVO cv, Model model) {

		return "/comm/viewPost";
	}
}
