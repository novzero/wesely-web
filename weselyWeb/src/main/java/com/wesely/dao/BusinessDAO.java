package com.wesely.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.BusinessDataVO;

@Mapper
public interface BusinessDAO {
	
	// <!-- 저장하기 : 사업자정보 저장 -->
	void insert(String bno, String bname, String bdate);

	
	// <!-- 삭제하기 : 사업자정보 삭제 -->
	void delete(BusinessDataVO businessVO);
	
	// <!-- 1개 가져오기 -->
	BusinessDataVO selectByBno(String bno);
	BusinessDataVO selectByBname(String bname);
	
	// <!-- 동일한 사업자등록번호 개수 얻기 : 사업자등록번호 중복 확인 -->
	int selectCountByBno(String bno);
	
	
}
