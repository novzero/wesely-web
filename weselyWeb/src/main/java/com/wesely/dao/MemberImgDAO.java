package com.wesely.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.MemberImgVO;


@Mapper
public interface MemberImgDAO {
		// 저장
		void insert(MemberImgVO memberImgVO);
		// 수정
		void update(MemberImgVO memberImgVO);
		// 삭제
		void delete(MemberImgVO memberImgVO);
		
		int getLastInsertedIdx();
		
}
