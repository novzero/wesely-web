package com.wesely.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wesely.service.MemberService;
import com.wesely.vo.MemberVO;

@RestController
@RequestMapping(value = "/api")
public class MemberApiController {

	@Autowired
	private MemberService memberService;
	// 목록
	@GetMapping(value = "/list")
	public List<MemberVO> getList(){
		return memberService.getList();
	}
	// userid로 찾기
	@GetMapping(value = "/list/{userid}")
	public MemberVO getUserId(@PathVariable String userid){
		return memberService.getByUserId(userid);
	}
	// 아이디로 찾기
	@GetMapping(value = "/{id}")
	public MemberVO getId(@PathVariable int id){
		return memberService.getById(id);
	}
	// 아이디 개수
	@GetMapping(value = "/count/{userid}")
	public int getUserIdCount(@PathVariable String userid){
		return memberService.getByUserIdCount(userid);
	}
	// 로그인
	@GetMapping("/login")
	public MemberVO login(@ModelAttribute MemberVO vo) {
		return memberService.login(vo);
	}
	
	
	
}
