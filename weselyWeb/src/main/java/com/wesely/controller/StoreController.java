package com.wesely.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wesely.vo.CommVO;

@Controller
@RequestMapping("/store")
public class StoreController {

		//운동시설 목록보기
		@RequestMapping(value = { "/", "/list" })
		public String getList(@ModelAttribute CommVO cv, Model model) {

			return "/store/placeList";
		}

		// 운동시설 상세보기
		@GetMapping(value = "/view")
		public String view(@ModelAttribute CommVO cv, Model model) {

			return "/store/placeView";
		}
}
