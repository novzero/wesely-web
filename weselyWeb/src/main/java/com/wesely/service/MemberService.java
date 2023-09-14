package com.wesely.service;

import java.util.List;

import com.wesely.vo.MemberVO;

public interface MemberService {
	// 로그인
	MemberVO login(MemberVO vo);
	
	// 아이디 중복확인
	int getByUserIdCount(String userid);
	
	// 1개 얻기
	MemberVO getById(int id);
	MemberVO getByUserId(String userid);
	
	// 모두 얻기
	List<MemberVO> getList();
	
	// 저장
	boolean insert(MemberVO VO);
	
	// 수정
	boolean update(MemberVO VO);
	
	// 삭제
	boolean delete(MemberVO VO);
	
	// 아이디 찾기
	MemberVO findUserId(MemberVO VO);
	
	// 비밀번호 찾기
	MemberVO findPassword(MemberVO VO);
	
}
