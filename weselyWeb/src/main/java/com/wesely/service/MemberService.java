package com.wesely.service;

import com.wesely.vo.MemberVO;

public interface MemberService {
	// 회원가입
	void insert(MemberVO memberVO);
	
	// 닉네임 수정
	boolean updateNickname(MemberVO memberVO);
	// 비밀번호 변경
	boolean updatePassword(MemberVO memberVO, String newPassword);
	
	// 회원 탈퇴
	boolean delete(MemberVO memberVO);
	
	// 로그인
	MemberVO login(MemberVO memberVO);
	
	// 로그아웃
	void logout();
	
	// 아이디 중복검사
	int idCheck(String userid);
	
	// 별명 중복검사
	int nicknameCheck(String nickname);
	
	// 전화번호 중복검사
	int phoneCheck(String phone);
	
	// 이메일 중복검사
	int emailCheck(String email);
	
	
	// 아이디 찾기
	MemberVO findUserId(MemberVO VO);
	
	// 비밀번호 찾기
	MemberVO findPassword(MemberVO VO);

	

}
