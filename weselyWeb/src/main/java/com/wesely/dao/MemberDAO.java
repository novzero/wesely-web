package com.wesely.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.MemberVO;

@Mapper
public interface MemberDAO {
	// <!-- 1. 전체 개수 얻기 -->
	int selectCount();
	// <!-- 2. 지정 아이디의 개수 얻기 : 아이디 중복 확인 처리 -->
	int selectByUserIdCount(String userid);
	
	// <!-- 3. 1개 가져오기 :로그인/정수수정/회원탈퇴 ... -->
	MemberVO selectByUserId(String userid);
	MemberVO selectById(int id);
	MemberVO selectByPhone(String phone);
	
	// <!-- 4. 전체가져오기 : 관리자가 회원 목록보기(나중에 조건별, 페이징 처리) -->
	List<MemberVO> selectByList();
	// <!-- 5. 저장 -->
	void insert(MemberVO memberVO);
	// <!-- 6. 수정 -->
	void update(MemberVO memberVO);
	// <!-- 7. 삭제 -->
	void delete(int id);
	
	// 임시 비번으로 비번 변경하기
	void updatePassword(HashMap<String, String> map);
}
