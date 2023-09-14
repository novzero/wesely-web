package com.wesely.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wesely.vo.CommunityVO;

@Service("communityService")
public class CommunityServiceImpl implements CommunityDAO{

	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommunityVO selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommunityVO> selectList(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return null;
	}
// 저장
	@Override
	public void insert(CommunityVO communityVO) {
		// TODO Auto-generated method stub
		
	}
// 수정
	@Override
	public void update(CommunityVO communityVO) {
		// TODO Auto-generated method stub
		
	}
// 삭제
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
// 조회수 증가
	@Override
	public void updateReadCount(int id) {
		// TODO Auto-generated method stub
		
	}

}
