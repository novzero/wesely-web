package com.wesely.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesely.dao.StoreDAO;
import com.wesely.dao.StoreImgDAO;
import com.wesely.dao.StoreReviewDAO;
import com.wesely.vo.StoreImgVO;
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

	@Autowired
	private StoreImgDAO storeImgDAO;

	// 목록보기&더보기
	@Override
	public List<StoreVO> selectMore(int num) {
		log.info("selectMore 호출 : {}", num);

		// 먼저 운동시설 정보를 가져온다.
		List<StoreVO> list = storeDAO.selectMore(num);

		// 각 시설에 대한 리뷰 개수와 별점평균 그리고 시설이미지리스트를 추가로 설정한다.
		if (list != null) {
			for (StoreVO storeVO : list) {
				// 리뷰개수
				storeVO.setReviewCount(storeReviewDAO.selectCountByRef(storeVO.getId()));
				// 별점평균
				storeVO.setAverageStar(storeReviewDAO.selectAverageStarByRef(storeVO.getId()));
				// 반복중인 storeVO ID에 해당하는 이미지를 가져온다.
				List<StoreImgVO> images = storeImgDAO.selectByRef(storeVO.getId());
				log.info("selectMore 이미지 : {}", images);
				// 가져온걸 이미지리스트에 집어넣는다.
				storeVO.setImgList(images);
			}
		}
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
				// 리뷰들 모두 가져온 것을 reviewList 라고 하자.
				List<StoreReviewVO> reviewList = storeReviewDAO.selectListByRef(id);
				// 리뷰 총 개수 가져온 것을 totalReview라고 하자.
				int totalReview = storeReviewDAO.selectCountByRef(id);

				// 3. 리뷰를 VO에 넣어준다.
				storeVO.setReviewList(reviewList);
				// 4. 리뷰의 총 개수를 VO에 넣어준다.
				storeVO.setReviewCount(totalReview);
			}
		} catch (Exception e) {
			log.error(" Store 아이디 찾는데 문제 발생 " + id, e);
			e.printStackTrace();
		}
		// -----------------------------------------------------------------------------
		log.info("findById 리턴 : {}", storeVO);
		return storeVO;
	}

	// 현재위치의 운동시설 데이터 저장
	@Override
	public boolean insert(StoreVO storeVO) {
		log.info(" 비즈니스 시설 등록 -- insert 호출 : {}", storeVO);
		boolean result = false;
		try {
			// 글 저장
			storeDAO.insert(storeVO);
			// 이미지 저장
			for(StoreImgVO images : storeVO.getImgList()) {
				storeImgDAO.insert(images);
			}
			return true;
		} catch (Exception e) {
			log.info(" 운동시설 데이터 저장 insert 리턴 : {}", storeVO);
			return result;
		}
	}

	// 현재위치의 운동시설 데이터 수정
	@Override
	public boolean update(StoreVO storeVO) {

		return false;
	}

	// 현재위치의 운동시설 데이터 삭제
	@Override
	public boolean delete(StoreVO storeVO) {

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
