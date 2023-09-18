package com.wesely.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesely.dao.MemberDAO;
import com.wesely.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("memberService")
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public void insert(MemberVO memberVO) {
		log.info("{}의 insert호출 : {}", this.getClass().getName(), memberVO);
		memberDAO.insert(memberVO);
	}

	@Override
	public void update(MemberVO memberVO) {
		log.info("{}의 update호출 : {}", this.getClass().getName(), memberVO);
		
	}

	@Override
	public void delete(MemberVO memberVO) {
		log.info("{}의 delete호출 : {}", this.getClass().getName(), memberVO);
		
	}

	@Override
	public void login(MemberVO memberVO) {
		log.info("{}의 login호출 : {}", this.getClass().getName(), memberVO);
		
	}

	@Override
	public void logout() {
		log.info("{}의 logout호출", this.getClass().getName());
		
	}

	@Override
	public List<MemberVO> selectList() {
		List<MemberVO> list = null;
		log.info("{}의 selectList 호출", this.getClass().getName());
		
		log.info("{}의 selectList 리턴 : {}", this.getClass().getName(), list);
		return list;
	}

	@Override
	public void emailCheck(String uuid, String userid) {
		log.info("{}의 emailCheck 호출 : {}", this.getClass().getName(), userid + "," + uuid);
		
	}

	@Override
	public int idCheck(String userid) {
		log.info("{}의 idCheck 호출 : {}", this.getClass().getName(), userid);
		int idcount = memberDAO.selectCountByUserid(userid);
		log.info("{}의 idCheck 리턴 : {}", this.getClass().getName(), idcount);
		return idcount;
	}
	
	@Override
	public int nicknameCheck(String nickname) {
		log.info("{}의 nicknameCheck 호출 : {}", this.getClass().getName(), nickname);
		int nickcount = memberDAO.selectCountByNickname(nickname);
		log.info("{}의 nicknameCheck 리턴 : {}", this.getClass().getName(), nickcount);
		return nickcount;
	}


	@Override
	public MemberVO findPassword(MemberVO memberVO) {
		log.info("{}의 findPassword 호출 : {}", this.getClass().getName(), memberVO);
		MemberVO memberVO2 = null;
		
		log.info("{}의 findPassword 리턴 : {}", this.getClass().getName(), memberVO2);
		return memberVO2;
	}

	@Override
	public MemberVO findUserId(MemberVO memberVO) {
		log.info("{}의 findUserId 호출 : {}", this.getClass().getName(), memberVO);
		MemberVO memberVO2 = null;
		
		log.info("{}의 findUserId 리턴 : {}", this.getClass().getName(), memberVO2);
		return memberVO2;
	}

}