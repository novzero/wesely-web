package com.wesely.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.GoodVO;

@Mapper
public interface goodDAO {
	int CountGood(int ref); // 좋아요 개수
	void insertGood(GoodVO goodVO); // 좋아요 등록
	void deleteGood(int nickname); // 좋아요 취소
	int goodCheck(Map<String, Object>map);
}
