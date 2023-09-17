package com.wesely.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesely.service.CommunityService;
import com.wesely.vo.CommVO;
import com.wesely.vo.CommentVO;
import com.wesely.vo.CommunityVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CommunityService communityService;
	
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
	
	// 댓글저장
	@PostMapping(value = "/commentInsert")
	@ResponseBody
	public boolean commentInsert(@ModelAttribute CommentVO vo) {
		log.info("댓글 저장 호출 : {}",vo);
		boolean result = false;
		try {
			result = communityService.commentInsert(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 저장 리턴 : {}",result);
		return result;
	}
	
	// 댓글수정
	@PostMapping(value = "/commentUpdate")
	@ResponseBody
	public boolean commentUpdate(@ModelAttribute CommentVO vo) {
		log.info("댓글 수정 호출 : {}",vo);
		boolean result = false;
		try {
			result = communityService.commentUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 수정 리턴 : {}",result);
		return result;
	}
	
	// 댓글삭제
	@PostMapping(value = "/commentDelete")
	@ResponseBody
	public boolean commentDelete(@ModelAttribute CommentVO vo) {
		log.info("댓글 삭제 호출 : {}",vo);
		boolean result = false;
		try {
			result = communityService.commentDelete(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 삭제 리턴 : {}",result);
		return result;
	}
}
