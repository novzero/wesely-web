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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wesely.service.StoreService;
import com.wesely.vo.MemberVO;
import com.wesely.vo.StoreImgVO;
import com.wesely.vo.StoreVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	private StoreService storeService;

	// 서버의 리소스를 접근하기위한 객체. 부트가 자동으로 로드해줍니다.
	@Autowired
	ResourceLoader resourceLoader;

	// 운동시설 저장하기
	// 입력폼
	@GetMapping(value = "/insert")
	public String insert(HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("mvo");

		if ("일반계정".equals(member.getAuthority())) {
			return "error/403"; // 일반 계정은 이 페이지에 접근할 수 없음
		}

		return "/business/createStore";
	}

	// 운동시설 저장하기
	@PostMapping(value = "/insertOk")
	public String insertOkPost(@ModelAttribute StoreVO storeVO, @RequestParam MultipartFile[] uploadFile,
			@RequestParam List<Integer> iorder, HttpServletRequest request, Model model, HttpSession session)
			throws IOException {
		MemberVO member = (MemberVO) session.getAttribute("mvo");
		String userid = member.getUserid();

		log.info("컨트롤러 ?: {}{}{}", storeVO, uploadFile);
		// 내용은 받았지만 파일은 받지 않았다.
		// 첨부파일들을 리스트 에 담아준다.
		List<StoreImgVO> list = new ArrayList<>();
		// 파일이 존재하면
		if (uploadFile != null && uploadFile.length > 0) {
			// 파일 저장 경로
			String filePath = getFilePath();
			log.info("서버 절대 경로 : " + filePath);
			// 업로드된 파일을 파일에 담아 반복수행한다.
			int index = 0;
			for (MultipartFile file : uploadFile) {
				// 파일이 있다면
				if (!file.isEmpty()) {
					// 랜덤으로 절대 경로 설정
					String uuid = UUID.randomUUID().toString();
					// 파일이름은 원본파일 이름을 가져온다.
					String fileName = file.getOriginalFilename();
					// 파일 속성도 원본파일 속성을 가져온다.
					String contentType = file.getContentType();
					// 파일 중복제거를 위한 키 _ 파일 이름
					File newFile = new File(filePath + uuid + "_" + fileName);
					// MultipartFile 객체의 내용(file)을 새롭게 생성한 File 객체(newFile)에 복사(저장) 합니다..
					file.transferTo(newFile);
					StoreImgVO storeImgVO = new StoreImgVO();
					storeImgVO.setUuid(uuid);
					storeImgVO.setFileName(fileName);
					storeImgVO.setContentType(contentType);

					// iOrder 값을 설정합니다.
					if (index < iorder.size()) {
						storeImgVO.setIorder(iorder.get(index));
						index++;
					}

					// 받아온 정보들을 리스트에다가 추가해준다.
					list.add(storeImgVO);
				}

			}
			storeVO.setImgList(list);
		}
		if (storeService.insert(storeVO)) {
			log.info("저장 성공");
		} else {
			log.info("저장 실패");
		}
		return "redirect:/store/view/b/" + userid;
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

	// -----------------------------------------------------------------------------------------
	// 스토어(운동시설) 수정

	// 수정 폼
	@GetMapping(value = "/update/{storeId}")
	public String updateForm(@PathVariable("storeId") int storeId, Model model) {
		// 아이디값으로 찾아내 가져오기
		StoreVO storeVo = storeService.findById(storeId);

		// 글이 없으면 없다고 만들라고 하는 페이지로 이동
		if (storeVo == null) {
			return "redirect:/business/noStore";
		}

		// 존재하면 폼 띄워주기위해 가져오기
		model.addAttribute("st", storeVo);

		return "/business/modifyStore";
	}

	// 수정하기 완료
	@GetMapping(value = "/updateOk")
	public String updateOkGet() {
		return "/business/businessView";
	}

	@PostMapping(value = "/updateOk")
	public String updateStore(@ModelAttribute StoreVO storeVO, // 글내용
	        @RequestParam(defaultValue = "") String delList, // 삭제파일 id들
	        @RequestParam MultipartFile[] uploadFile, // 파일들
	        @RequestParam String[] iorder, // 이미지 순서들
	        HttpServletRequest request, Model model) throws IOException {
	    log.info("수정하는 이미지 파일 , 컨트롤러 : {}{}{}", storeVO, uploadFile);
	    String userid = storeVO.getUserid();
	    
	    if (uploadFile != null && uploadFile.length > 0) { 
	        List<StoreImgVO> list = new ArrayList<>();
	        String filePath = getFilePath(); 

	        for (int i=0; i<uploadFile.length; i++) { 
	            MultipartFile file = uploadFile[i];

	            if (!file.isEmpty()) { 
	                String uuid = UUID.randomUUID().toString();
	                String fileName = file.getOriginalFilename();
	                String contentType = file.getContentType();

	                File newFile = new File(filePath + uuid + "_" + fileName);
	                file.transferTo(newFile);

	                StoreImgVO storeImgVO = new StoreImgVO();
	                storeImgVO.setUuid(uuid);
	                storeImgVO.setFileName(fileName);
	                storeImgVO.setContentType(contentType);
	                
	                // ref값을 원본의 id로 넣는다.
	                storeImgVO.setRef(storeVO.getId()); 
	             // Convert to int only if necessary
	                if (iorder[i] != null && !iorder[i].isEmpty()) {
	                	storeImgVO.setIorder(Integer.parseInt(iorder[i]));
	                }

	    			list.add(storeImgVO);
	    		}
	        }

	        storeVO.setImgList(list);  // 리스트를 VO에 저장한다.
	    }

	if(storeService.update(storeVO,delList,

	getFilePath())) {
			log.info("수정 성공");
		} else {
			log.info("수정 실패");
		}

		return "redirect:/store/view/b/" + userid;
	}

	// 스토어(운동시설) 삭제
//	@GetMapping(value = "/delete")
//	public String deleteForm(@ModelAttribute StoreVO st, Model model) {
//		StoreVO storeVO = storeService.findById(st.getId());
//		if (storeVO == null) { // 글이 존재하지 않으면 리스트로
//			return "redirect:/";
//		}
//		// 존재하면 내용보기로
//		model.addAttribute("store", storeVO);
//
//		return "board/delete";
//	}

	// 삭제하기 완료
	@GetMapping(value = "/deleteOk")
	public String deleteOkGet() {
		return "redirect:/store/";
	}

	@PostMapping(value = "/deleteOk")
	public String deleteOkPost(@ModelAttribute StoreVO storeVO // 글 내용
	) throws IOException {
		// 서비스를 호출하여 실제로 DB에 삭제를 해주자!!!
		if (storeService.delete(storeVO.getId(), getFilePath())) {
			log.info("삭제 성공!!!!");
		} else {
			log.info("삭제 실패!!!!");
		}
		return "redirect:/store/";
	}
}
