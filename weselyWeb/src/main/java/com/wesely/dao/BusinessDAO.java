package com.wesely.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.MemberVO;

@Mapper
public interface BusinessDAO {
	
	// 저장하기: 사업자 정보 저장
	void insert(MemberVO memberVO);

	// 동일한 사업자등록번호 개수 얻기: 사업자등록번호 중복 확인
	int selectCountByBno(String bno);
}
