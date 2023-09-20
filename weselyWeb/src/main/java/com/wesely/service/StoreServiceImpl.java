package com.wesely.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesely.dao.StoreDAO;
import com.wesely.dao.StoreReviewDAO;
import com.wesely.vo.StoreVO;

import lombok.extern.slf4j.Slf4j;

@Service("storeService")
@Slf4j
public class StoreServiceImpl implements StoreService{
	@Autowired
	private StoreDAO storeDAO;
	
//	@Autowired
//	private StoreReviewDAO storeReviewDAO;

	@Override
	public List<StoreVO> selectMore(int num) {
		log.info("selectMore 호출 : {},{},{}", num);
		List<StoreVO> moreVIew = null;
		//-----------------------------------------------------------------------------
		// 1. 페이지 계산을 위하여 전체개수를 구한다.
		int totalCount = storeDAO.selectCount();
		// 2. 가져오게 넘기기
		List<StoreVO> list = storeDAO.selectMore(num);
		
		return list;
	}

	@Override
	public StoreVO selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreVO view(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(StoreVO storeVO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(StoreVO storeVO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(StoreVO storeVO) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
