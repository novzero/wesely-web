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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wesely.service.CommunityService;
import com.wesely.vo.CommVO;
import com.wesely.vo.CommentVO;
import com.wesely.vo.CommunityImgVO;
import com.wesely.vo.CommunityVO;
import com.wesely.vo.Paging;

import jakarta.servlet.http.HttpServletRequest;
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
		Paging<CommunityVO> paging = communityService.selectList(cv.getP(), cv.getS(), cv.getB());
		model.addAttribute("cv",cv);
		model.addAttribute("pv", communityService.selectList(cv.getP(), cv.getS(), cv.getB()));
		return "/community/list";
	}

	// 커뮤니티 저장하기
	// 입력폼
	@GetMapping(value = "/insert")
	public String insert(@ModelAttribute CommVO cv, Model model) {
		model.addAttribute("cv",cv);
		return "/community/createPost";
	}
	// 저장완료
	@GetMapping(value = "/insertOk")
	public String insertOkGet() {
		return "redirect:/community/list";
	}
	// 저장 포스트
	@PostMapping(value = "/insertOk")
	public String insertOkPost(
			@ModelAttribute CommVO cv,
			@ModelAttribute CommunityVO vo,
			@RequestParam MultipartFile[] uploadFile,
			HttpServletRequest request,Model model) throws IOException {
		log.info("컨트롤러 ?: {}{}{}", cv, vo, uploadFile);
		// 내용은 받았지만 파일은 받지 않았다.
		// 첨부파일 처리를 여기서 해준다.
		List<CommunityImgVO> list = new ArrayList<>();
		// 파일이 존재하면
		if(uploadFile != null && uploadFile.length > 0) { 
			// 파일 저장 경로
			String filePath = getFilePath(); 
			log.info("서버 절대 경로 : " + filePath);
			for (MultipartFile file : uploadFile) {
				 // 파일이 있다면
				if (!file.isEmpty()) {
					// 랜덤으로 절대 경로 설정
					String uuid = UUID.randomUUID().toString();
					String fileName = file.getOriginalFilename();
					String contentType = file.getContentType();
					// 파일 중복제거를 위한 키 _ 파일 이름
					File newFile = new File(filePath + uuid + "_" + fileName);
					file.transferTo(newFile);
					CommunityImgVO communityImgVO = new CommunityImgVO();
					communityImgVO.setUuid(uuid);
					communityImgVO.setFileName(fileName);
					communityImgVO.setContentType(contentType);
					list.add(communityImgVO);
				}
			}
		vo.setImgList(list);
	}
		if (communityService.insert(vo)) {
			log.info("저장 성공");
		}else {
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
		model.addAttribute("community",vo);
		model.addAttribute("cv",cv);
		return "community/update";
	}
	// 수정하기 완료
	@GetMapping(value = "/updateOk")
	public String updateOkGet() {
		return "redirect:/community/list";
	}
	
	@PostMapping(value = "/updateOk")
	
	// 서버경로
	private String getFilePath() throws IOException{
		String filePath = resourceLoader.getResource("/").getURI().toString() + "static/images/upload/";
		filePath = filePath.substring(6);
		File f = new File(filePath); // 파일 객체 생성
		if (!f.exists())
			f.mkdirs(); // 파일(폴더)이 존재하지 않으면 폴더를 생성한다.
		return filePath;
	}
	
	// 커뮤니티 상세보기
	@GetMapping(value = "/view")
	public String view(@ModelAttribute CommVO cv, Model model) {
		CommunityVO vo = communityService.selectById(cv.getId(), cv.getMode());
		if(vo == null) {
			return "redirect:/community/list?p=1&b=" + cv.getB() + "&s=" + cv.getS();
		}
		model.addAttribute("community",vo);
		model.addAttribute("cv",cv);
		return "community/viewPost";
	}
	
	// 댓글저장
	@PostMapping(value = "/commentInsert")
	@ResponseBody
	public boolean commentInsert(@ModelAttribute CommentVO vo) {
		log.info("댓글 저장 호출 : {}",vo);
		boolean result = false;
		try {
			result = communityService.commentInsert(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 저장 리턴 : {}",result);
		return result;
	}
	
	// 댓글수정
	@PostMapping(value = "/commentUpdate")
	@ResponseBody
	public boolean commentUpdate(@ModelAttribute CommentVO vo) {
		log.info("댓글 수정 호출 : {}",vo);
		boolean result = false;
		try {
			result = communityService.commentUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 수정 리턴 : {}",result);
		return result;
	}
	
	// 댓글삭제
	@PostMapping(value = "/commentDelete")
	@ResponseBody
	public boolean commentDelete(@ModelAttribute CommentVO vo) {
		log.info("댓글 삭제 호출 : {}",vo);
		boolean result = false;
		try {
			result = communityService.commentDelete(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("댓글 삭제 리턴 : {}",result);
		return result;
	}
}
