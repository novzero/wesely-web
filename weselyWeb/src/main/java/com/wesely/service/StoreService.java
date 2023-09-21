package com.wesely.service;

import java.util.List;

import com.wesely.vo.StoreVO;

public interface StoreService {
	// 기능 1개당 메서드 1개
	
		// 1. 목록보기
		List<StoreVO> selectMore(int num);
		
		// 2. 1개 얻기 : 상세보기/수정/삭제
		StoreVO selectById(int id);
		
		// 2. 내용보기
		StoreVO view(int id);
		
		// 3. 새글쓰기
		boolean insert(StoreVO storeVO);
		
		// 4. 수정하기
		boolean update(StoreVO storeVO);
		
		// 5. 삭제하기
		boolean delete(StoreVO storeVO);
		
		//6. 카카오에서 받은 데이터
		StoreVO save(StoreVO storeVO);
	
}
