package com.wesely.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wesely.service.StoreService;
import com.wesely.vo.StoreVO;



@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	StoreService storeService;
	
	@GetMapping(value = "/store")
	public List<StoreVO> getList(@RequestParam(defaultValue = "12")int lastNum ) {
		return storeService.selectMore(lastNum);
	}
}
