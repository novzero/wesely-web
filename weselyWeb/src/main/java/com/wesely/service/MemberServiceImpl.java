package com.wesely.service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesely.dao.BusinessDAO;
import com.wesely.dao.CommentDAO;
import com.wesely.dao.CommunityDAO;
import com.wesely.dao.MemberDAO;
import com.wesely.dao.MemberImgDAO;
import com.wesely.vo.MemberImgVO;
import com.wesely.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	BusinessDAO businessDAO;
	@Autowired
	CommunityDAO communityDAO;
	@Autowired
	CommentDAO commentDAO;
	MemberImgDAO memberImgDAO;

	// 일반회원정보 저장
	@Override
	public void insert(MemberVO memberVO) {
		log.info("{}의 insert호출 : {}", this.getClass().getName(), memberVO);
		if (memberVO.getAuthority().equals("비즈니스계정")) {
			// 비즈니스 계정인 경우 사업자 정보도 함께 저장

			memberVO.setBno(memberVO.getBNum1() + memberVO.getBNum2() + memberVO.getBNum3());

			memberDAO.insert(memberVO);
			int idx = memberDAO.getLastInsertedIdx();

			memberVO.setRef(idx);
			log.info("{}의 insert호출 : {}", this.getClass().getName(), memberVO);
			businessDAO.insert(memberVO);

		} else {
			memberDAO.insert(memberVO);
		}
	}

	// 회원 탈퇴
	@Override
	public boolean delete(MemberVO memberVO) {
		boolean result = false;
		log.info("{}의 delete호출 : {}", this.getClass().getName(), memberVO);
		MemberVO dbVO = memberDAO.selectByUserid(memberVO.getUserid());
		if (dbVO != null) {
			// 입력한 비밀번호가 db정보와 일치한다면
			if (dbVO.getPassword().equals(memberVO.getPassword())) {
				// communityDAO.deleteNickname(dbVO.getNickname());
				memberDAO.delete(memberVO); // 탈퇴
				result = true;
			}
		}
		return result;
	}

	// 로그인
	@Override
	public MemberVO login(MemberVO vo) {
		log.info("{}의 login호출 : {}", this.getClass().getName(), vo);
		MemberVO memberVO = null;
		try {
			// 넘어온 아이디가 존재하는지 판단
			MemberVO mvo = memberDAO.selectByUserid(vo.getUserid());
			log.info("{}의 selectByUserid호출 : {}", this.getClass().getName(), vo);
			
			if (mvo != null) { // 지정 아이디의 회원이 있다면
				if (mvo.getPassword().equals(vo.getPassword())) {
					memberVO = mvo;
				} else {
					// 비밀번호가 일치하지 않는다
					throw new Exception("비밀번호가 일치하지 않습니다.");
				}
			} else {
				// 아이디가 없다
				 throw new Exception("아이디가 존재하지 않습니다.");
			}
		} catch (Exception e) {
			log.error("로그인 실패: {}", e.getMessage());
			e.printStackTrace();
		}
		log.info("login({}) 리턴", vo, memberVO);
		return memberVO;
	}

	@Override
	public void logout() {
		log.info("{}의 logout호출", this.getClass().getName());

	}

	// 아이디 중복체크
	@Override
	public int idCheck(String userid) {
		log.info("{}의 idCheck 호출 : {}", this.getClass().getName(), userid);
		int idcount = memberDAO.selectCountByUserid(userid);
		log.info("{}의 idCheck 리턴 : {}", this.getClass().getName(), idcount);
		return idcount;
	}

	// 닉네임 중복체크
	@Override
	public int nicknameCheck(String nickname) {
		log.info("{}의 nicknameCheck 호출 : {}", this.getClass().getName(), nickname);
		int nickcount = memberDAO.selectCountByNickname(nickname);
		log.info("{}의 nicknameCheck 리턴 : {}", this.getClass().getName(), nickcount);
		return nickcount;
	}

	// 전화번호 중복체크
	@Override
	public int phoneCheck(String phone) {
		log.info("{}의 phoneCheck 호출 : {}", this.getClass().getName(), phone);
		int phonecount = memberDAO.selectCountByPhone(phone);
		log.info("{}의 phoneCheck 리턴 : {}", this.getClass().getName(), phonecount);
		return phonecount;
	}

	// 이메일 중복체크
	@Override
	public int emailCheck(String email) {
		log.info("{}의 emailCheck 호출 : {}", this.getClass().getName(), email);
		int emailcount = memberDAO.selectCountByEmail(email);
		log.info("{}의 emailCheck 리턴 : {}", this.getClass().getName(), emailcount);
		return emailcount;
	}

	// 아이디 찾기
	@Override
	public MemberVO findUserId(MemberVO VO) {
		log.info("findUserId({}) 호출", VO);
		MemberVO memberVO = null;

		try {
			// 전화번호로 찾기
			MemberVO dbVO = memberDAO.selectByPhone(VO.getPhone());
			if (dbVO != null) { // 전화번호가 있고
				// 이름도 같으면
				if (dbVO.getUsername().equals(VO.getUsername())) {
					memberVO = dbVO;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("findUserId({}) 리턴 : {}", VO, memberVO);
		return memberVO;
	}

	// 비밀번호 찾기
	@Override
	public MemberVO findPassword(MemberVO VO) {
		log.info("findPassword({}) 호출", VO);
		MemberVO memberVO = null;

		try {
			// 아이디로 찾기
			MemberVO dbVO = memberDAO.selectByUserid(VO.getUserid());
			if (dbVO != null) { // 아이디가 있고
				// 이메일도 같으면
				if (dbVO.getEmail().equals(VO.getEmail())) {
					// 임시 비밀 번호를 만들고
					String newPassword = MakePassword.makePassword(10);

					// DB에 비밀번호를 임시 비번으로 변경하고
					HashMap<String, String> map = new HashMap<>();
					map.put("userid", dbVO.getUserid());
					map.put("password", newPassword);
					memberDAO.updatePassword1(map);

					// 새로운 임시비밀번호로 저장
					dbVO.setPassword(newPassword);
					memberVO = dbVO;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("findPassword({}) 리턴 : {}", VO, memberVO);
		return memberVO;
	}
	
	// 아이디로 회원정보 조회
	public MemberVO findUserById(String userId) {
	    log.info("{}의 findUserById 호출 : {}", this.getClass().getName(), userId);
	    MemberVO memberVO = memberDAO.selectByUserid(userId);
	    log.info("{}의 findUserById 리턴 : {}", this.getClass().getName(), memberVO);
	    return memberVO;
	}

	// 닉네임 변경
	public boolean updateNickname(MemberVO memberVO) {
		boolean result = false;
		log.info("{}의 updateNickname호출 : {}", this.getClass().getName(), memberVO);
		MemberVO dbVO = memberDAO.selectByUserid(memberVO.getUserid());

		if (dbVO != null) {
			// 게시판의 정보를 변경
			HashMap<String, String> map = new HashMap<>();
			// 기존 닉네임이 null 이면 아이디로 저장.
			String oldNickname = (dbVO.getNickname() == null || dbVO.getNickname().isEmpty()) ? dbVO.getUserid()
					: dbVO.getNickname();
			String oldName = oldNickname;
			map.put("newNickname", memberVO.getNickname());
			map.put("oldNickname", oldNickname);
			map.put("userid", memberVO.getUserid());
			map.put("newName", memberVO.getNickname());
			map.put("oldName", oldName);

			try {
				commentDAO.updateName(map);
				communityDAO.updateNickname(map);
				memberDAO.updateNickname(map);
				// 회원정보변경
				result = true;
			} catch (Exception e) {
				// 예외 처리
				log.error("닉네임 변경 중 오류 발생: {}", e.getMessage());

			}
		}
		return result;

	}

	// 비밀번호 변경
	@Override
	public boolean updatePassword(MemberVO memberVO, String newPassword) {
		boolean result = false;
		log.info("{}의 updatePassword 호출 : {}", this.getClass().getName(), memberVO);
		MemberVO dbVO = memberDAO.selectByUserid(memberVO.getUserid());
		if (dbVO != null) {
			// 입력한 비밀번호가 db정보와 일치한다면
			if (dbVO.getPassword().equals(memberVO.getPassword())) {
				// 비밀번호를 바꾼다
				memberVO.setPassword(newPassword);
				memberDAO.updatePassword(memberVO);
				result = true;
			}
		}
		return result;
	}

//==============================================================================================================
	
	// 이미지 경로조회
	@Override
    public Resource loadMemberImage(String fileName) {
        // 실제 서버 디렉토리 경로 설정
        String imagePath = "/static/images/";

        try {
            Path filePath = Paths.get(imagePath + fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("이미지 파일을 찾을 수 없습니다: " + fileName);
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new RuntimeException("이미지 파일 로딩 중 오류 발생", e);
        }
    }
	
	// 프로필 이미지 등록
	@Override
	public void saveImage(MemberVO memberVO) {
		log.info("{}의 saveImage 호출 : {}", this.getClass().getName(), memberVO);

		MultipartFile imageFile = memberVO.getImageFile();

		// MemberImgVO 객체 생성 및 필드 설정
		MemberImgVO memberImg = new MemberImgVO();
		memberImg.setUuid(UUID.randomUUID().toString()); // UUID 생성 또는 다른 고유 식별자 사용 가능
		memberImg.setFileName(imageFile.getOriginalFilename());
		memberImg.setContentType(imageFile.getContentType());
		
		int idx = memberDAO.getLastInsertedIdx();
		memberImg.setRef(idx);

		// 데이터베이스에 이미지 정보 저장하기 위해 DAO 호출
		memberImgDAO.insert(memberImg);
	};

//==============================================================================================================

	// 사업자번호 중복체크 + 사업자번호 검증

	// api 서비스키 할당
	private static final String serviceKey = "hLHWpZv6BDxRiqMCB%2FXVu0aIlJq%2FiiIUl%2FFt%2BOH9wHYxyCM4FF3E5pwVZ%2B0OHlFC0by91hiA%2BXvyjnaJya%2BIsA%3D%3D";

	@Override
	public int bnoCheck(String bno) throws Exception {
		log.info("{}의 bnoCheck 호출 : {}", this.getClass().getName(), bno);
		// 사업자번호 중복체크
		int result = businessDAO.selectCountByBno(bno);
		log.info("{}의 bnoCheck 리턴 : {}", this.getClass().getName(), result);
		// 중복되지 않은 경우에 사업자번호 유효성 검증을 진행한다.
		if (result == 0) {
			String url = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + serviceKey;

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json; charset=utf-8");

			JSONObject jsonObject = new JSONObject(); // JSON 객체 생성
			JSONArray jsonArray = new JSONArray(); // JSON 배열 생성
			jsonArray.put(bno); // 사업자번호 추가
			jsonObject.put("b_no", jsonArray); // JSON 배열 추가

			String body = jsonObject.toString(); // JSON 문자열로 변환

			HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
			// RestTemplate 객체 생성
			RestTemplate restTemplate = new RestTemplate();
			// API 호출
			ResponseEntity<String> responseEntity = 
					restTemplate.exchange(new URI(url), HttpMethod.POST, requestEntity,
					String.class);

			HttpStatus httpStatus = (HttpStatus) responseEntity.getStatusCode();

			String response = responseEntity.getBody();
			// JSON 문자열을 맵으로 변환
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(response, new TypeReference<Map<String, Object>>() {
			});

			if (httpStatus == HttpStatus.OK) {
				if (map.containsKey("match_cnt") && map.get("match_cnt") instanceof Integer
						&& (Integer) map.get("match_cnt") == 1) {
					System.out.println("유효");
					result = 0;
				} else {
					System.out.println("없음");
					result = 2;
				}
			} else {
				System.out.println("API 호출 실패: " + httpStatus);
				result = -1;
			}
		}

		return result;

	}

}