package com.wesely.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesely.dao.StoreDAO;
import com.wesely.dao.StoreImgDAO;
import com.wesely.dao.StoreReviewDAO;
import com.wesely.vo.CommunityImgVO;
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
				// 리뷰의 별점 평균을 가져온 것을 averageStar라고 하자.
				double averageStar = storeReviewDAO.selectAverageStarByRef(id);
				// 매장 사진리스트 가져온 것을 imgList라고 하자.
				List<StoreImgVO> imgList = storeImgDAO.selectByRef(id);
				
				// 3. 리뷰를 VO에 넣어준다.
				storeVO.setReviewList(reviewList);
				// 4. 리뷰의 총 개수를 VO에 넣어준다.
				storeVO.setReviewCount(totalReview);
				// 5. 리뷰의 별점 평균을 VO에 넣어준다.
				storeVO.setAverageStar(averageStar);
				// 6. 매장 사진리스트를 VO에 넣어준다.
				storeVO.setImgList(imgList);
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

	// 운동시설 데이터 수정
	@Override
	public boolean update(StoreVO storeVO, String delList, String filePath) {
		log.info("update({},{},{}) 호출:", storeVO, delList, filePath);
		boolean result = false;
		// 글 존재하면 
		if (storeVO != null) {
			// 수정
			storeDAO.update(storeVO);
			
			// 파일저장 커뮤니티내용에있는 이미지리스트를 vo에 넣어서 
			for (StoreImgVO vo : storeVO.getImgList()) {
				vo.setRef(storeVO.getId());  // Set the correct 'ref' value for each image.
	            // 수정 -> 실제로는 새로운 이미지 추가하는 동작임.
	            storeImgDAO.modify(vo);
	        
			}
			// 삭제 파일을 삭제한다.
			if (delList != null && delList.length() > 0) {
				String[] delFile = delList.trim().split(" ");
				if (delFile != null && delFile.length > 0) {
					for (String s : delFile) {
						// 파일 삭제
						// 서버 저장된 파일삭제
						StoreImgVO storeImgVO = storeImgDAO.selectById(Integer.parseInt(s));
						log.info("삭제할 이미지 정보: {}", storeImgVO);
						// 파일이름은 vo에 있는 uuid(랜덤명 wakwuefh223) + _ +파일명(pepe)
						String fileName = storeImgVO.getUuid() + "_" + storeImgVO.getFileName();
						// filePath (위치할 경로) fileName(파일이름) 을 file 에 담는다
						File file = new File(filePath, fileName);
						log.info("파일 경로" + file.getAbsolutePath());
						log.info("삭제할 파일 경로: {}", file.getPath());
						if (file.exists()) { // 파일이 존재하면
	                        boolean deleteResult = file.delete();
	                        log.info("{} 파일 삭제 결과: {}", file.getPath(), deleteResult);
							
						} else {
						    log.warn("삭제할 파일 {} 가 존재하지 않습니다.", file.getPath());
						}
						// db의 정보도 삭제
						storeImgDAO.deleteById(Integer.parseInt(s));
	                    
					}
					result = true;
				}
			}
		}
		log.info("update 리턴: {}", result);
		return result;

	}

	// 운동시설 삭제
	@Override
	public boolean delete(StoreVO storeVO, String delList) {
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
	
	// 비즈니스계정회원 아이디로 매장상세보기 가져오기
	@Override
	public StoreVO findByUserId(String userid) {
		 log.info("findByUserId 호출 : {}", userid);
		    StoreVO storeVO = null;
		    try {
				// 1. 해당 시설 id의 글을 읽어온다.
		    	storeVO = storeDAO.findByUserId(userid);
				
		    	  // 2. 해당 글이 존재한다면 추가 정보들을 가져온다.
		        if (storeVO != null) {
		            int id = storeVO.getId();  // 가져온 매장의 ID

		            // 리뷰들 모두 가져온 것을 reviewList 라고 하자.
		            List<StoreReviewVO> reviewList = storeReviewDAO.selectListByRef(id);
		            // 리뷰 총 개수 가져온 것을 totalReview라고 하자.
		            int totalReview = storeReviewDAO.selectCountByRef(id);
		            // 리뷰의 별점 평균을 가져온 것을 averageStar라고 하자.
		            double averageStar = storeReviewDAO.selectAverageStarByRef(id);
		            // 매장 사진리스트 가져온 것을 imgList라고 하자.
		            List<StoreImgVO> imgList = storeImgDAO.selectByRef(id);

		            // 3. 위 정보들을 VO에 넣어준다.
		            storeVO.setReviewList(reviewList);
		            storeVO.setReviewCount(totalReview);
		            storeVO.setAverageStar(averageStar);
		            storeVO.setImgList(imgList); 
		        }
		        
			} catch (Exception e) {
				log.error(" Store 아이디 찾는데 문제 발생 " + userid, e);
				e.printStackTrace();
			}
			// -----------------------------------------------------------------------------
			log.info("findByUserId 리턴 : {}", storeVO);
			return storeVO;
	}

	
	

}
