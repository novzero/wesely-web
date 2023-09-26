package com.wesely.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesely.dao.StoreDAO;
import com.wesely.dao.StoreReviewDAO;
import com.wesely.vo.StoreReviewVO;
import com.wesely.vo.StoreVO;

import lombok.extern.slf4j.Slf4j;

@Service("storeService")
@Slf4j
public class StoreServiceImpl implements StoreService {
	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private StoreReviewDAO storeReviewDAO;

	// 더보기
	@Override
	public List<StoreVO> selectMore(int num) {
		log.info("selectMore 호출 : {}", num);

		// 먼저 운동시설 정보를 가져온다.
		List<StoreVO> list = storeDAO.selectMore(num);

		// 각 시설에 대해 리뷰 개수를 추가로 설정한다.
		if (list != null) {
			for (StoreVO storeVO : list) {
				storeVO.setReviewCount(storeReviewDAO.selectCountByRef(storeVO.getId()));
			}
		}
		//log.info("selectMore 리턴 : {}", );
		log.info("selectMore 리턴 : {}", list);
		return list;
	}

	// 운동시설 상세보기
	@Override
	public StoreVO findById(int id) {
		log.info("findById 호출 : {}", id);
		StoreVO storeVO = null;
		// -----------------------------------------------------------------------------
		try {
			// 1. 해당 시설 id의 글을 읽어온다.
			storeVO = storeDAO.findById(id);
			// 2. 해당 글이 존재한다면 리뷰들을 모두 가져온다.
			if (storeVO != null) {
				List<StoreReviewVO> reviewList = storeReviewDAO.selectListByRef(id);
				// 3. 리뷰를 VO에 넣어준다.
				storeVO.setReviewList(reviewList);
			}
		} catch (Exception e) {
			log.error("Error occurred while finding a store with ID " + id, e);
			e.printStackTrace();
		}
		// -----------------------------------------------------------------------------
		log.info("view 리턴 : {}", storeVO);
		return storeVO;
	}

	// 현재위치의 운동시설 데이터 저장
	@Override
	public boolean insert(StoreVO storeVO) {
		try {
			storeDAO.insert(storeVO);
			return true;
		} catch (Exception e) {
			log.error("Error occurred while inserting a store", e);
			return false;
		}
	}

	// 현재위치의 운동시설 데이터 수정
	@Override
	public boolean update(StoreVO storeVO) {
		// TODO Auto-generated method stub
		return false;
	}

	// 현재위치의 운동시설 데이터 삭제
	@Override
	public boolean delete(StoreVO storeVO) {
		// TODO Auto-generated method stub
		return false;
	}

	// 현재위치의 운동시설 목록들 데이터저장
	@Override
	public StoreVO save(StoreVO store) {
		// Create a map with the name and address of the store
		Map<String, Object> params = new HashMap<>();
		params.put("name", store.getName());
		params.put("address", store.getAddress());

		// DB에 중복되는 시설명과 시설주소가 있는지 확인
		if (storeDAO.selectByNameAndAddress(params) == null) {
			// 만약 없다면 저장
			storeDAO.insert(store);
		}
		return store;
	}

	// id로 리뷰 찾기
	@Override
	public StoreReviewVO getReview(Long id) {
		return storeReviewDAO.selectById(id);
	}

	// 리뷰 저장
	@Override
	public boolean reviewInsert(StoreReviewVO storeReivewVO) {
		log.info("reviewInsert 호출 : {}", storeReivewVO);
		boolean result = false;
		// -----------------------------------------------------------------------------
		try {
			if (storeReivewVO != null) {
				storeReviewDAO.insert(storeReivewVO);
				result = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		// -----------------------------------------------------------------------------
		log.info("reviewInsert 리턴 : {}", result);
		return result;
	}

	// 리뷰 수정
	@Override
	public boolean reviewUpdate(StoreReviewVO storeReivewVO) {
		log.info("reviewUpdate 호출 : {}", storeReivewVO);
		boolean result = false;
		// -----------------------------------------------------------------------------
		try {
			if (storeReivewVO != null) {
				storeReviewDAO.update(storeReivewVO);
				result = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		// -----------------------------------------------------------------------------
		log.info("reviewUpdate 리턴 : {}", result);
		return result;
	}

	// 리뷰 삭제
	@Override
	public boolean reviewDelete(Long id) {
		log.info("reviewDelete 호출 : {}", id);
		boolean result = false;

		if (id != null) {
			storeReviewDAO.delete(id);
			result = true;
		}

		log.info("reviewDelete 결과 : {}", result);
		return result;
	}

}
