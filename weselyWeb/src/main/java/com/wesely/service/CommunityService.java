package com.wesely.service;

import com.wesely.vo.CommentVO;
import com.wesely.vo.CommunityVO;
import com.wesely.vo.Paging;

public interface CommunityService {
	// 목록보기
	Paging<CommentVO> selectList(int currentPage, int sizeOfPage,int sizeOfBlock);
	
	// 1개얻어 조회수증가
	CommunityVO selectById(int id, int mode);

	// 내용보기
	CommunityVO view(int id);

	// 저장 새글쓰기
	boolean insert(CommunityVO communityVO);

	// 수정
	boolean update(CommunityVO communityVO);

	// 삭제
	boolean delete(CommunityVO communityVO);

	// 댓글쓰기
	boolean commentInsert(CommentVO commentVO);

	// 댓글수정
	boolean commentUpdate(CommentVO commentVO);

	// 댓글삭제
	boolean commentDelete(CommentVO commentVO);
}