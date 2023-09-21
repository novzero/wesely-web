package com.wesely.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wesely.service.StoreService;
import com.wesely.vo.StoreVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class APIController {

	@Autowired
	StoreService storeService;

	@GetMapping(value = "/store")
	public List<StoreVO> getList(@RequestParam(defaultValue = "12") int lastNum) {
		return storeService.selectMore(lastNum);
	}

	@PostMapping(value = "/addStore")
	public String addStore(@ModelAttribute StoreVO store) {
		log.info("===== {}", store);
		storeService.save(store);
		return "redirect:/store";
	}
}
