package com.wesely.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wesely.dao.CommentDAO;
import com.wesely.service.CommunityService;
import com.wesely.vo.CommVO;
import com.wesely.vo.CommentVO;
import com.wesely.vo.CommunityImgVO;
import com.wesely.vo.CommunityVO;
import com.wesely.vo.GoodVO;
import com.wesely.vo.Paging;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CommunityService communityService;

	// 서버의 리소스를 접근하기위한 객체. 부트가 자동으로 로드해줍니다.
	@Autowired
	ResourceLoader resourceLoader;

	// 커뮤니티 목록보기
	@RequestMapping(value = { "/", "/list" })
	public String getList(@ModelAttribute CommVO cv, Model model) {
		// 글 페이지,페이지당 갯수, 하단페이지를 저장해서
		Paging<CommunityVO> paging = communityService.selectList(cv.getP(), cv.getS(), cv.getB());
		// html 에다가 뿌려줌
		model.addAttribute("cv", cv);
		model.addAttribute("pv", paging);
		return "/community/list";
	}

	// 커뮤니티 저장하기
	// 입력폼
	@GetMapping(value = "/insert")
	public String insert(@ModelAttribute CommVO cv, Model model) {
		model.addAttribute("cv", cv);
		return "/community/createPost";
	}

	// 저장완료
	@GetMapping(value = "/insertOk")
	public String insertOkGet() {
		return "redirect:/community/list";
	}

	// 저장 포스트
	@PostMapping(value = "/insertOk")
	public String insertOkPost(@ModelAttribute CommVO cv, @ModelAttribute CommunityVO vo,
			@RequestParam MultipartFile[] uploadFile, HttpServletRequest request, Model model) throws IOException {
		log.info("컨트롤러 ?: {}{}{}", cv, vo, uploadFile);
		// 내용은 받았지만 파일은 받지 않았다.
		// 첨부파일들을 리스트 에 담아준다.
		List<CommunityImgVO> list = new ArrayList<>();
		// 파일이 존재하면
		if (uploadFile != null && uploadFile.length > 0) {
			// 파일 저장 경로
			String filePath = getFilePath();
			log.info("서버 절대 경로 : " + filePath);
			// 업로드된 파일을 파일에 담아 반복수행한다.
			for (MultipartFile file : uploadFile) {
				// 파일이 있다면
				if (!file.isEmpty()) {
					// 랜덤으로 절대 경로 설정
					// uuid = uuid+1231231515#$@#. 문자열로 생성 중복값 피할려고 랜덤으로 덧붙임
					String uuid = UUID.randomUUID().toString();
					// 파일이름은 원본파일 이름을 가져온다.
					String fileName = file.getOriginalFilename();
					// 파일 속성도 원본파일 속성을 가져온다.
					String contentType = file.getContentType();
					// 파일 중복제거를 위한 키 _ 파일 이름
					File newFile = new File(filePath + uuid + "_" + fileName);
					// MultipartFile 객체의 내용(file)을 새롭게 생성한 File 객체(newFile)에 복사(저장) 합니다..
					file.transferTo(newFile);
					CommunityImgVO communityImgVO = new CommunityImgVO();
					communityImgVO.setUuid(uuid);
					communityImgVO.setFileName(fileName);
					communityImgVO.setContentType(contentType);
					// 받아온 정보들을 리스트에다가 추가해준다.
					list.add(communityImgVO);
				}
			}
			vo.setImgList(list);
		}
		if (communityService.insert(vo)) {
			log.info("저장 성공");
		} else {
			log.info("저장 실패");
		}
		return "redirect:/community/list?p=1&b=" + cv.getB() + "&s=" + cv.getS();
	}

	// 수정 폼
	@GetMapping(value = "/update")
	public String updateForm(@ModelAttribute CommVO cv, Model model) {
		CommunityVO vo = communityService.selectById(cv.getId(), cv.getMode());
		// 글이 없으면 리스트페이지로
		if (vo == null) {
			return "redirect:/community/list?p=1&b=" + cv.getB() + "&s=" + cv.getS();
		}
		// 존재하면 상세보기로
		model.addAttribute("community", vo);
		model.addAttribute("cv", cv);
		return "community/update";
	}

	// 수정하기 완료
	@GetMapping(value = "/updateOk")
	public String updateOkGet() {
		return "redirect:/community/list";
	}

	@PostMapping(value = "/updateOk")
	public String updatePost(@ModelAttribute CommVO cv, @ModelAttribute CommunityVO vo,
			@RequestParam(defaultValue = "") String delList, @RequestParam MultipartFile[] uploadFile,
			HttpServletRequest request, Model model) throws IOException {
		log.info("컨트롤러 ?: {}{}{}", cv, vo, uploadFile);
		// 내용은 받았지만 파일은 받지 않았다.
		// 첨부파일 처리를 여기서 해준다.
		// 파일이 존재하면
		if (uploadFile != null && uploadFile.length > 0) {
			List<CommunityImgVO> list = new ArrayList<>();
			// 파일 저장 경로
			String filePath = getFilePath();
			log.info("서버 절대 경로 : " + filePath);
			for (MultipartFile file : uploadFile) {
				// 파일이 있다면
				if (!file.isEmpty()) {
					// 랜덤으로 절대 경로 설정
					// uuid = uuid+1231231515#$@#. 문자열로 생성 중복값 피할려고 랜덤으로 덧붙임
					String uuid = UUID.randomUUID().toString();
					// 파일이름은 원본파일 이름을 가져온다.
					String fileName = file.getOriginalFilename();
					// 파일 속성도 원본파일 속성을 가져온다.
					String contentType = file.getContentType();
					// 파일 중복제거를 위한 키 _ 파일 이름
					File newFile = new File(filePath + uuid + "_" + fileName);
					// MultipartFile 객체의 내용(file)을 새롭게 생성한 File 객체(newFile)에 복사(저장) 합니다..
					file.transferTo(newFile);
					
					CommunityImgVO communityImgVO = new CommunityImgVO();
					communityImgVO.setUuid(uuid);
					communityImgVO.setFileName(fileName);
					communityImgVO.setContentType(contentType);
					// 받아온 정보들을 리스트에다가 추가해준다.
					list.add(communityImgVO);
				}
			}
			vo.setImgList(list);
		}
		// 서비스 호출해서 DB에 저장하기
		if (communityService.update(vo, delList, getFilePath())) {
			log.info("수정 성공");
		} else {
			log.info("수정 실패");
		}
		return "redirect:/community/list?p=1&b=" + cv.getB() + "&s=" + cv.getS();
	}

	// 삭제하기 폼
	@GetMapping(value = "/delete")
	public String deleteForm(@ModelAttribute CommVO cv, Model model) {
		// 그 페이지의 조회수, 아이디를 불러와서 vo에 담음
		CommunityVO vo = communityService.selectById(cv.getId(), cv.getMode());
		if (vo == null) {
			return "redirect:/community/list?p=1&b=" + cv.getB() + "&s=" + cv.getS();
		}
		model.addAttribute("community", vo);
		model.addAttribute("cv", cv);
		return "community/delete";
	}

	// 삭제하기 완료
	@GetMapping(value = "/deleteOk")
	public String deleteOkGet() {
		return "/redirect:/community/list";
	}

	@PostMapping(value = "/deleteOk")
	public String deleteOkPost(@ModelAttribute CommVO cv, @ModelAttribute CommunityVO vo) throws IOException {
		// 글내용하고 파일이있으면 삭제
		if (communityService.delete(vo, getFilePath())) {
			log.info("삭제 성공");
		} else {
			log.info("삭제 실패");
		}
		return "redirect:/community/list?p=1&b=" + cv.getB() + "&s=" + cv.getS();
	}

	// 서버경로
	private String getFilePath() throws IOException {
		// 리소스 로더를 사용하여 서버의 루트 경로("/")에 접근하고, 그 경로에 "static/images/upload/"를 추가합니다.
	    // 이렇게 하면 업로드된 이미지가 저장될 폴더의 절대 경로가 생성됩니다.
	    String filePath = resourceLoader.getResource("/").getURI().toString() + "static/images/upload/";
	    
	    // getResource 메소드는 'file:/' 형태의 프리픽스를 포함한 URI 문자열을 반환하므로, 
	    // 실제 파일 시스템 경로만 얻기 위해 앞의 6개 문자('file:/')를 제거합니다.
	    filePath = filePath.substring(6);

	    // 지정된 경로에 해당하는 File 객체를 생성합니다. 
	    File f = new File(filePath); 

	     // 만약 해당 폴더가 존재하지 않으면 폴더를 생성합니다. 
	     if (!f.exists())
	         f.mkdirs(); 

	     // 완성된 파일 저장 경로를 반환합니다.
	     return filePath;
	}

	// 커뮤니티 상세보기
	@GetMapping(value = "/view")
	public String view(@ModelAttribute CommVO cv, Model model) {
		CommunityVO vo = communityService.selectById(cv.getId(), cv.getMode());
		// 상세내용이 없으면 목록보기로 돌려보냄
		if (vo == null) {
			return "redirect:/community/list?p=1&b=" + cv.getB() + "&s=" + cv.getS();
		}
		model.addAttribute("community", vo);
		model.addAttribute("cv", cv);
		return "community/viewPost";
	}

	// 댓글저장
	@PostMapping(value = "/commentInsert")
	@ResponseBody
	public boolean commentInsert(@ModelAttribute CommentVO vo) {
		log.info("댓글 저장 호출 : {}", vo);
		boolean result = false;
		try {
			result = communityService.commentInsert(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 저장 리턴 : {}", result);
		return result;
	}

	// 댓글수정
	@PutMapping(value = "/commentUpdate")
	@ResponseBody
	public boolean commentUpdate(@ModelAttribute CommentVO vo) {
		log.info("댓글 수정 호출 : {}", vo);
		boolean result = false;
		try {
			result = communityService.commentUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 수정 리턴 : {}", result);
		return result;
	}

	// 댓글삭제
	@DeleteMapping(value = "/commentDelete")
	@ResponseBody
	public boolean commentDelete(@ModelAttribute CommentVO vo) {
		log.info("댓글 삭제 호출 : {}", vo);
		boolean result = false;
		try {
			result = communityService.commentDelete(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 삭제 리턴 : {}", result);
		return result;
	}

	// 로그인 폼 처리하기
	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		// 쿠키에 저장된 사용자아이디가 있으면 읽어서 간다.
		Cookie[] cookies = request.getCookies();
		String userid = null;
		// userid변수에 쿠키에 userid가 있다면 읽어서 대입하자
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userid")) {
					userid = cookie.getValue();
					break;
				}
			}
		}
		// 모델에 쿠키값을 저장한다.
		model.addAttribute("userid", userid);
		// 로그인 폼으로 포워딩 한다.
		return "/member/login";
	}

	// 좋아요 저장했을때 호출 주소
		@PostMapping(value = "/goodInsert")
		@ResponseBody
		public boolean goodInsert(@ModelAttribute GoodVO go) {
			log.info("좋아요 저장 호출 : {}", go);
			boolean result = false;
			try {
				result = communityService.goodInsert(go);
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("좋아요 저장 리턴 : {}", result);
			return result;
		}
		// 좋아요 삭제했을때 호출 주소
		@DeleteMapping(value = "/goodDelete/{ref}/{nickname}")
		@ResponseBody
		public boolean goodDelete(@PathVariable int ref, @PathVariable String nickname) {
		    GoodVO go = new GoodVO();
		    go.setRef(ref);
		    go.setNickname(nickname);

		    log.info("좋아요 삭제 호출 : {}", go);
		    boolean result = false;
		    try {
		        result = communityService.goodDelete(go);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    log.info("좋아요 삭제 리턴 : {}", result);

		    return result;
		}  	
		
		@GetMapping("/countGood/{postId}")
	    @ResponseBody  // 이 어노테이션이 있어야 데이터만 리턴 가능합니다.
	    public int countGood(@PathVariable("postId") int postId) {
	        return communityService.countGood(postId);
	    }
}
