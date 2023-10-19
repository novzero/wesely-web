package com.wesely.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wesely.service.StoreService;
import com.wesely.vo.StoreVO;

@EnableScheduling
@Controller // 메인컨트롤러
public class HomeController {

	@Autowired
	StoreService storeService;

	// 시작 파일 지정
	@RequestMapping(value = "/")
	public String home(Model model) {
		List<StoreVO> stores = storeService.getAllSlides();
		model.addAttribute("st", stores);
		return "index";
	}

	@RequestMapping(value = "/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {

		if (error != null) {
			model.addAttribute("error", "아이디나 비번이 존재하지 않습니다.");
		}
		if (logout != null) {
			model.addAttribute("msg", "로그아웃 성공!!!");
		}
		return "login";
	}
}