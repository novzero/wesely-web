package com.wesely.controller;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wesely.service.BusinessService;
import com.wesely.vo.BusinessDataVO;

@RestController
public class BusinessApiController {
    
    private static final Logger logger = LoggerFactory.getLogger(BusinessApiController.class);
    
    @Autowired
    BusinessService businessService;
 
    @RequestMapping(value = "/getBuisnessData", method = RequestMethod.GET)
    public Object getObject(@RequestBody BusinessDataVO vo) throws UnsupportedEncodingException{
        
        String bno = vo.getBno();
        String bname = vo.getBname();
        // UnsupportedEncodingException 예외처리 해주지 않으면 여기에서 ERROR
        Object response = businessService.getBusinessData(bno, bname);
        logger.info("##### RESULT: "+response);
        return response;
        
    } 
}	