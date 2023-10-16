package com.wesely.service;

import java.util.List;

import com.wesely.vo.StoreReviewVO;
import com.wesely.vo.StoreVO;

public interface StoreService {
	// 기능 1개당 메서드 1개

	// 1. 목록보기
	List<StoreVO> selectMore(int num);

	// 2. 1개 얻기 : 상세보기/수정/삭제
	StoreVO findById(int id);

	// 3. 새글쓰기
	boolean insert(StoreVO storeVO);

	// 4. 수정하기
	boolean update(StoreVO storeVO, String delList, String filePath);

	// 5. 삭제하기
	boolean delete(int id, String filePath);

	// 6. 카카오에서 받은 데이터
	StoreVO save(StoreVO storeVO);

	// 7. 리뷰 얻기
	StoreReviewVO getReview(Long id);

	// 8. 리뷰 쓰기
	boolean reviewInsert(StoreReviewVO storeReivewVO);

	// 9. 리뷰 수정
	boolean reviewUpdate(StoreReviewVO storeReivewVO);

	// 10. 리뷰 삭제
	boolean reviewDelete(Long id);

	StoreVO findByUserId(String userid);
	
	// 12. 이미지 순서 변경
	boolean updateImageOrder(int id, int newOrder);
	
	// 여러 개의 kakaoID로 운동시설 정보 조회하기 
	List<StoreVO> findStoresByIds(List<String> ids);

}
