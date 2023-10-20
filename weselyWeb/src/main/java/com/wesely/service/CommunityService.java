package com.wesely.service;

import java.util.Map;

import com.wesely.vo.CGoodVO;
import com.wesely.vo.CommentVO;
import com.wesely.vo.CommunityVO;
import com.wesely.vo.GoodVO;
import com.wesely.vo.MemberVO;
import com.wesely.vo.Paging;

public interface CommunityService {
	// 목록보기
	Paging<CommunityVO> selectList(int currentPage, int sizeOfPage,int sizeOfBlock);
	
	// 1개얻어 조회수증가
	CommunityVO selectById(int id, int mode);

	// 내용보기
	CommunityVO view(int id);

	// 저장 새글쓰기
	boolean insert(CommunityVO communityVO);

	// 수정
	boolean update(CommunityVO communityVO , String delList, String filePath);

	// 삭제
	boolean delete(CommunityVO communityVO,String filePath);

	// 댓글쓰기
	boolean commentInsert(CommentVO commentVO);

	// 댓글수정
	boolean commentUpdate(CommentVO commentVO);

	// 댓글삭제
	boolean commentDelete(CommentVO commentVO);
	
	//좋아요 등록
	boolean goodInsert(GoodVO goodVO);
	
	//좋아요 삭제
	boolean goodDelete(GoodVO goodVO);
	
	//좋아요 확인
	int goodCheck(Map<String, Object>map);
	
	// 좋아요 개수 가져오기
	int countGood(int ref);
	
	//댓글 좋아요 등록
	boolean cgoodInsert(CGoodVO cGoodVO);
	
	//댓글 좋아요 삭제
	boolean cgoodDelete(CGoodVO cGoodVO);
	
	//댓글 좋아요 확인
	int cgoodCheck(Map<String, Object>map);
	
	//댓글 좋아요 개수 가져오기
	int ccountGood(int ref);
}