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
import org.springframework.web.multipart.MultipartFile;

import com.wesely.service.StoreService;
import com.wesely.vo.StoreImgVO;
import com.wesely.vo.StoreVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = {"/business", "/"})
public class BusinessController {
	
	@Autowired
	private StoreService storeService;

	// 서버의 리소스를 접근하기위한 객체. 부트가 자동으로 로드해줍니다.
	@Autowired
	ResourceLoader resourceLoader;
	
	// 운동시설 저장하기
			// 입력폼
			@GetMapping(value = "/insert")
			public String insert() {
				
				return "/business/createStore";
			}
		
		// 운동시설 저장하기
		@PostMapping(value = "/insertOk")
		public String insertOkPost(@ModelAttribute StoreVO storeVO,
				@RequestParam MultipartFile[] uploadFile, HttpServletRequest request, Model model) throws IOException {
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
						StoreImgVO storeImgVO = new StoreImgVO();
						storeImgVO.setUuid(uuid);
						storeImgVO.setFileName(fileName);
						storeImgVO.setContentType(contentType);
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
			return "redirect:/";
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
}