package com.wesely.service;

import com.wesely.vo.CommunityVO;

public interface CommunityService {
	// 내용보기
	CommunityVO selectById(int id,int mode);
	// 저장하기
	boolean insert(CommunityVO communityVO);
	// 수정
	boolean update(CommunityVO communityVO);
	// 삭제
	boolean delete(CommunityVO communityVO);
}
