package com.wesely.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.CGoodVO;
import com.wesely.vo.GoodVO;

@Mapper
public interface cgoodDAO {
	int CommCountGood(int ref); //댓글 좋아요 개수
	void CommInsertGood(CGoodVO cGoodVO); //댓글 좋아요 등록
	void CommDeleteGood(CGoodVO cGoodVO); //댓글 좋아요 취소
	int CommGoodCheck(Map<String, Object>map); //댓글 좋아요 확인 여부
}
