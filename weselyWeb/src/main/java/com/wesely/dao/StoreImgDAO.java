package com.wesely.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.StoreImgVO;

@Mapper
public interface StoreImgDAO {
	// 전체 읽기
		List<StoreImgVO> selectByRef(int ref);
		// 저장
		void insert(StoreImgVO storeImgVO);
		// 수정
		void modify(StoreImgVO storeImgVO);
		// 글의 이미지 전체 삭제
		void deleteByRef(int ref);
		// 지정 id 파일 삭제
		void deleteById(int id);
		// 1개 얻기
		StoreImgVO selectById(int id);
}
