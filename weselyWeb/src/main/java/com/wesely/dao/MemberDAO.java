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
	void update(MemberVO memberVO);
	
	// <!-- 삭제하기 : 회원탈퇴 -->
	void delete(int id);
	
	
	// <!-- 1개 가져오기 : 수정/회원탈퇴/로그인 ... -->
	MemberVO selectByIdx(int idx);
	MemberVO selectByUserid(String userid);
	
	// <!-- 전체 개수 얻기 : 관리자 모드 -->
	int selectCount();
	
	// <!-- 전체 가져오기 : 관리자가 회원 목록보기(나중에 조건별, 페이징 처리) -->
	List<MemberVO> selectAll();
	
	// <!-- 동일한 아이디의 개수 얻기 : 아이디 중복 확인 -->
	int selectCountByUserid(String userid);
	
	// <!-- 동일한 별명의 개수 얻기 : 별명 중복 확인 -->
	int selectCountByNickname(String nickname);
	
	// <!-- 동일한 이메일의 개수 얻기 : 이메일 중복 확인 -->
	int selectCountByEmail(String email);
	
	// <!-- 이름으로 찾기 -->
	List<MemberVO> selectByUsername(String username);
	
	// 임시 비번으로 비번 변경하기
	void updatePassword(HashMap<String, String> map);
}
