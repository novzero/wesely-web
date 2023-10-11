package com.wesely.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BusinessService {

	public Object getBusinessData(String bno, String bname) throws UnsupportedEncodingException {
		String url = "https://api.odcloud.kr/api/nts-businessman/v1/status";
		String serviceKey = "hLHWpZv6BDxRiqMCB%2FXVu0aIlJq%2FiiIUl%2FFt%2BOH9wHYxyCM4FF3E5pwVZ%2B0OHlFC0by91hiA%2BXvyjnaJya%2BIsA%3D%3D";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		String decodeServiceKey = URLDecoder.decode(serviceKey, "UTF-8");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("serviceKey", decodeServiceKey)
				.queryParam("bno", bno).queryParam("bname", bname).build(false); // 자동 Encoding 막기

		Object response = restTemplate.getForObject(uri.toUriString(), String.class);

		return response;
	}

}