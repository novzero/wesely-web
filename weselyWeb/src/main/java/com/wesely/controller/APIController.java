package com.wesely.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesely.service.StoreService;
import com.wesely.vo.StoreReviewVO;
import com.wesely.vo.StoreVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class APIController {

	@Autowired
	StoreService storeService;

	// 운동시설 조회하기 => 여기에 현재위치정보까지 받아서 조회하게 만들기
	// @RequestParam : 'lastNum' 쿼리 파라미터 값을 해당 메소드의 'lastNum' 매개변수에 바인딩
	// 해당 쿼리 파라미터가 없으면 기본값으로 12
	@GetMapping(value = "/store")
	public List<StoreVO> getList(@RequestParam(defaultValue = "12") int lastNum) {

		return storeService.selectMore(lastNum);
	}
	
	@GetMapping(value = "/stores")
	public List<StoreVO> getStoreList(@RequestParam(defaultValue = "12") int lastNum) {

		return storeService.selectMore(lastNum);
	}

	
	// 운동시설 정보를 데이터베이스에 저장
	// @RequestBody : HTTP 요청 본문(Body)을 자바 객체로 변환하여 해당 메소드의 매개변수에 바인딩
	// JSON 형태의 운동시설 리스트 정보를 'stores' 리스트 객체로 변환
	@PostMapping(value = "/addStores")
	public ResponseEntity<?> addStores(@RequestBody List<StoreVO> stores) {
		log.info("시설 추가 addStores 호출 : {}", stores);
		StoreVO storeList = null;
		for (StoreVO store : stores) {
			try {
				// db에 없는 시설만 저장
				storeList = storeService.save(store);
			} catch (Exception e) {
				log.info("시설저장중 발생 에러", e);
			}
		}
		int refId = storeList.getId();
		log.info("시설 추가 addStores 리턴 : {}", storeList);
		return ResponseEntity.ok(refId);
	}

	// 운동시설 kakaoID 리스트로 운동시설 정보 조회
	@PostMapping(value = "/getStoresByIds")
	public List<StoreVO> getStoresByIds(@RequestBody List<String> ids) {
		log.info("kakaoID로 db 조회 호출 : {}", ids);
		List<StoreVO> storeList = null;
		try {
			storeList = storeService.findStoresByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("kakaoID로 db 조회 결과: {}", storeList);
		return storeList;
	}
	
	// 현재위치loc으로 운동시설 정보 조회
		@PostMapping(value = "/getStoreByLoc")
		public List<StoreVO> getStoreByLoc(@RequestBody String loc) {
			log.info("Loc현재위치로 db 조회 호출 : {}", loc);
			List<StoreVO> storeList = null;
			try {
				storeList = storeService.findStoresByLoc(loc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("Loc현재위치로 db 조회 결과: {}", storeList);
			return storeList;
		}

// -----------------------------------------------------------------------------------------
	// 시설에 대한 리뷰저장
	@PostMapping(value = "/reviewInsert")
	@ResponseBody
	public boolean reviewInsert(@ModelAttribute StoreReviewVO storerReviewVO) {
		log.info("리뷰 저장 호출 : {}", storerReviewVO);
		boolean result = false;
		try {
			result = storeService.reviewInsert(storerReviewVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("리뷰 저장 리턴 : {}", result);
		return result;
	}

	// 리뷰 수정
	@PutMapping(value = "/reviewUpdate")
	@ResponseBody
	public boolean reviewUpdate(@ModelAttribute StoreReviewVO storerReviewVO) {
		log.info("리뷰 수정 호출 : {}", storerReviewVO);
		boolean result = false;
		try {
			result = storeService.reviewUpdate(storerReviewVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("리뷰 수정 리턴 : {}", result);
		return result;
	}

	// 리뷰 삭제
	@DeleteMapping(value = "/reviewDelete/{id}")
	@ResponseBody
	public boolean reviewDelete(@PathVariable("id") Long id) {
		log.info("리뷰 삭제 호출 : {}", id);
		boolean result = false;
		try {
			result = storeService.reviewDelete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("리뷰 삭제 결과 : {}", result);
		return result;
	}

	// 리뷰 수정위해 해당 리뷰 1개 가져오기
	@GetMapping(value = "/reviewByID/{id}")
	@ResponseBody
	public StoreReviewVO reviewByID(@PathVariable("id") Long id) {
		log.info("해당 리뷰 1개 가져오기=== reviewByID 호출 : {}", id);
		StoreReviewVO storeReviewVO = null;
		try {
			storeReviewVO = storeService.getReview(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("reviewByID 결과 : {}", storeReviewVO);
		return storeReviewVO;
	}

}
