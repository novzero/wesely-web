package com.wesely.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.StoreVO;

@Mapper
public interface StoreDAO {
	//--------------------------------------------
		// mapper파일의 태그 1개당 메서드 1개
		// resultType이 리턴 타입
		// parameterType이 매개변수 타입
		// id가 메서드명
		//--------------------------------------------
		
		// 1. 전체 개수 얻기
		int selectCount();
		
		// 2. 1개 얻기 : 내용보기/수정/삭제
		StoreVO findById(int id);
		
		// 3. 추가 아이템 얻기 : 더보기
		List<StoreVO> selectMore(int num);
		
		// 4. 저장하기
		void insert(StoreVO storeVO);
		
		// 5. 수정하기
		void update(StoreVO storeVO);
		
		// 6. 삭제하기 
		void delete(int id);
		
		// 7. 사용자의 현재 위치 주변 운동시설들 받아온 데이터를 데이터베이스에 저장하기 전에 중복된 것이 있는지 확인하기
		StoreVO selectByNameAndAddress(Map<String, Object> params);
		
		// 8. 비즈니스계정 : 아이디로 시설정보가져오기
		StoreVO findByUserId(String userid);
		
		// 9. 여러 개의 카카오ID에 해당하는 운동시설 찾기
		List<StoreVO> selectByKakaoIds(List<String> ids);

		// 10. region 지역(현재위치)에 해당하는 운동시설 찾기
		List<StoreVO> selectByLoc(String loc); 
		
}
