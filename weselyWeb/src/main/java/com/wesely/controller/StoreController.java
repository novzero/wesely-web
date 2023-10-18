package com.wesely.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesely.service.StoreService;
import com.wesely.vo.MemberVO;
import com.wesely.vo.StoreVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/store")
@Slf4j
public class StoreController {

	@Autowired
	private StoreService storeService;

	// 운동시설 목록보기
	@RequestMapping(value = "/")
	public String getList() {

		return "/store/placeList2";
	}

	// 운동시설 상세보기 : 일반계정
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable("id") int id, Model model, HttpSession session, HttpServletRequest request) {
		StoreVO store = storeService.findById(id);

		log.info("view 운동시설 상세보기 호출 : {}", id);
		try {
			// 로그인이 되어있지 않을 때 로그인페이지로 이동
			if (session.getAttribute("mvo") == null) {
				String prevPage = request.getRequestURL().toString();
				session.setAttribute("prevPage", prevPage); // 현재 요청 URI를 세션에 저장

				return "redirect:/member/login";
			}
			
			if (store != null) {
				model.addAttribute("st", store);

				log.info("view 운동시설 상세보기 리턴 : {}", store);
				return "/store/placeView";
			} else {
				return "redirect:/store/";
			}
		} catch (Exception e) {
			return "redirect:/store/";
		}
	}

	// 운동시설 상세보기 : 비즈니스 계정 ==> 매장관리 NAV바 메뉴 클릭 시
	@GetMapping(value = "/view/b/{userid}")
	public String businessView(@PathVariable("userid") String userid, Model model, HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("mvo");

		if (member == null || !member.getUserid().equals(userid)) {
			return "error/403"; // 본인 id로 다른 비즈니스 스토어페이지에 접근할 수 없음
		}

		if ("일반계정".equals(member.getAuthority())) {
			return "error/403"; // 일반 계정은 이 페이지에 접근할 수 없음
		}

		StoreVO store = storeService.findByUserId(userid);

		if (store != null) {
			log.info("운동시설 비즈니스 상세보기==================================");
			log.info("findByUserId 호출 : {}", userid);
			model.addAttribute("st", store);
			return "/business/businessView";
		} else {
			return "business/noStore";
		}
	}

	// 운동시설 상세보기 : 카카오 API 데이터 => db에 저장되지않은 데이터 상세보기
	@GetMapping(value = "/view/ka/{id}")
	public String kakaoView(@PathVariable("id") String id, @RequestParam("data") String placeDataJson, Model model,
			HttpSession session, HttpServletRequest request) {
		log.info("카카오상세보기 호출 {}", placeDataJson);

		try {
			// 로그인이 되어있지 않을 때 로그인페이지로 이동
			if (session.getAttribute("mvo") == null) {
				String prevPage = request.getRequestURL().toString();
				// 요청 URI는 경로(path)만을 나타내며, 쿼리 스트링(?data=...)은 별도로 처리
				if (request.getQueryString() != null) {
					prevPage += "?" + request.getQueryString();
				}
				session.setAttribute("prevPage", prevPage); // 현재 요청 URI를 세션에 저장

				return "redirect:/member/login";
			}

			// JSON 문자열을 Java 객체로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> place = objectMapper.readValue(placeDataJson, new TypeReference<Map<String, Object>>() {
			});

			// 모델에 장소 데이터 추가
			model.addAttribute("st", place);
			log.info("st 호출 {}", place);
			return "/store/kakaoPlaceView";
		} catch (Exception e) {
			log.info("카카오상세보기 에러", e);

			return "redirect:/store/";
		}
	}

	// 운동시설 검색하기
	@GetMapping(value = "/search")
	public String search() {

		return "/store/placeSearch";
	}

}
