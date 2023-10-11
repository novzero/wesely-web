
package com.wesely.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wesely.service.BusinessService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RestController
public class BusinessApiController {

	@Autowired
	BusinessService businessService;

	// 사업자번호 중복확인
	@RequestMapping(value = "/bnoCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String bnoCheck(@RequestParam String bno) {
		log.info("{}의 bnoCheck 호출 : {}", this.getClass().getName(), bno);
		int count = businessService.bnoCheck(bno);
		log.info("{}의 bnoCheck 리턴 : {}", this.getClass().getName(), count);
		return count + "";
	}
}

/*
 * @PostMapping("/authenticate") private static final Logger logger =
 * LoggerFactory.getLogger(BusinessApiController.class); private static final
 * String url = "https://api.odcloud.kr/api/nts-businessman/v1/status";
 * 
 * public ResponseEntity<String> authenticateBusiness(@RequestBody
 * BusinessService business) { RestTemplate restTemplate = new RestTemplate();
 * 
 * HttpHeaders headers = new HttpHeaders();
 * headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
 * 
 * MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
 * params.add("businessId", business.getBusinessNumber()); params.add("apiKey",
 * business.getApiKey());
 * 
 * HttpEntity<MultiValueMap<String, String>> httpEntity = new
 * HttpEntity<>(params, headers);
 * 
 * ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
 * httpEntity, String.class);
 * 
 * logger.info("Response status: {}", response.getStatusCode());
 * logger.info("Response body: {}", response.getBody());
 * 
 * return response; } }
 * 
 */