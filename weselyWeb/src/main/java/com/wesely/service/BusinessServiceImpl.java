package com.wesely.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesely.dao.BusinessDAO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@Service("businessService")
public class BusinessServiceImpl implements BusinessService{
	@Autowired
	BusinessDAO businessDAO;
	
	
	// 사업자번호 중복체크
		@Override
		public int bnoCheck(String bno) {
			log.info("{}의 bnoCheck 호출 : {}", this.getClass().getName(), bno);
			int bnocount = businessDAO.selectCountByBno(bno);
			log.info("{}의 bnoCheck 리턴 : {}", this.getClass().getName(), bnocount);
			return bnocount;
		}
}