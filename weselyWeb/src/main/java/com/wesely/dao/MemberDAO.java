package com.wesely.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.MemberVO;

@Mapper
public interface MemberDAO {
	
	// <!-- 저장하기 : 회원가입 -->
	void insert(MemberVO memberVO);
	
	// <!-- 수정하기 : 회원정보 수정 -->
	void updateNickname(HashMap<String, String> map);
	void updatePassword(MemberVO memberVO);
	
	// 임시 비번으로 비밀번호 변경
	void updatePassword1(HashMap<String, String> map);
	
	// <!-- 삭제하기 : 회원탈퇴 -->
	void delete(MemberVO memberVO);
	
	
	// <!-- 1개 가져오기 : 수정/회원탈퇴/로그인 ... -->
	MemberVO selectByIdx(int idx);
	MemberVO selectByUserid(String userid);
	MemberVO selectByUsername(String username);
	MemberVO selectByNickname(String nickname);
	MemberVO selectByPassword(String password);
	MemberVO selectByPhone(String phone);
	
	
	// <!-- 동일한 아이디의 개수 얻기 : 아이디 중복 확인 -->
	int selectCountByUserid(String userid);
	
	// <!-- 동일한 별명의 개수 얻기 : 별명 중복 확인 -->
	int selectCountByNickname(String nickname);
	
	// <!-- 동일한 전화번호 개수 얻기 : 전화번호 중복 확인 -->
	int selectCountByPhone(String phone);
	
	// <!-- 동일한 이메일 개수 얻기 : 이메일 중복 확인 -->
	int selectCountByEmail(String email);
	
	
	// <!-- 전체 개수 얻기 : 관리자 모드 -->
	int selectCount();
	
	// <!-- 전체 가져오기 : 관리자가 회원 목록보기(나중에 조건별, 페이징 처리) -->
	List<MemberVO> selectAll();
	
	
}
