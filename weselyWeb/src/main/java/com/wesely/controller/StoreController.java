package com.wesely.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wesely.service.StoreService;
import com.wesely.vo.CommVO;
import com.wesely.vo.CommunityImgVO;
import com.wesely.vo.CommunityVO;
import com.wesely.vo.StoreVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/store")
@Slf4j
public class StoreController {

	@Autowired
	private StoreService storeService;

	// 운동시설 목록보기
	@RequestMapping(value = "/")
	public String getList() {
		
		return "/store/placeList";
	}

	// 운동시설 상세보기 : 일반계정
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable("id") int id, Model model) {
	    StoreVO store = storeService.findById(id);
	    
	    if (store != null) {
	    	log.info("운동시설 상세보기==================================");
	    	log.info("findById 호출 : {}", id);
	        model.addAttribute("st", store);
	        return "/store/placeView";
	    } else {
	        return "redirect:/store/";
	    }
	}
	
	// 운동시설 상세보기 : 비즈니스 계정 ==> 매장관리 NAV바 메뉴 클릭 시
	@GetMapping(value = "/view/b/{userid}")
	public String businessView(@PathVariable("userid") String userid, Model model) {
	    StoreVO store = storeService.findByUserId(userid);
	    
	    if (store != null) {
	    	log.info("운동시설 비즈니스 상세보기==================================");
	    	log.info("findByUserId 호출 : {}", userid);
	        model.addAttribute("st", store);
	        return "/business/businessView";
	    } else {
	        return "redirect:/";
	    }
	}

	// 운동시설 검색하기
	@GetMapping(value = "/search")
	public String search() {

		return "/store/placeSearch";
	}
	
}
