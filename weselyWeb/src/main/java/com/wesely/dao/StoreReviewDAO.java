package com.wesely.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.StoreReviewVO;

@Mapper
public interface StoreReviewDAO {
	// 1. ref(해당시설)에 대한 리뷰의 개수 구하기 : 목록보기에 리뷰 개수 출력
	int selectCountByRef(int ref);  
	// 2. ref(해당시설)에 대한 모든 리뷰 보기 (얻기)
	List<StoreReviewVO> selectListByRef(int ref);
	// 3. 리뷰 저장
	void insert(StoreReviewVO vo);
	// 4. 리뷰 수정
	void update(StoreReviewVO vo);
	// 5. 리뷰 1개 삭제
	void delete(Long id);
	// 6. 지정 ref대한 모든 리뷰 삭제하기 (비즈니스 계정에서?)????
	void deleteByRef(int ref);
	// 7. 댓글 1개 얻기(기존 리뷰를 얻어서 수정 폼에 띄우는 역할)
	StoreReviewVO selectById(Long id);
}
