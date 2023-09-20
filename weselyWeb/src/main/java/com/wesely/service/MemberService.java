package com.wesely.service;

import java.util.List;

import com.wesely.vo.MemberVO;

public interface MemberService {
	// 회원가입
	void insert(MemberVO memberVO);
	
	// 회원 정보 수정
	void update(MemberVO memberVO);
	
	// 회원 탈퇴
	void delete(MemberVO memberVO);
	
	// 로그인
	MemberVO login(MemberVO memberVO);
	
	// 로그아웃
	void logout();
	
	// 회원 목록 보기
	List<MemberVO> selectList();
	
	// 이메일 인증
	void emailCheck(String uuid, String userid);
	
	//아이디 중복검사
	int idCheck(String userid);
	
	//별명 중복검사
	int nicknameCheck(String nickname);
	
	// 아이디 찾기
	MemberVO findUserId(MemberVO memberVO);
	
	// 비밀번호 찾기
	MemberVO findPassword(MemberVO memberVO);
	

}
