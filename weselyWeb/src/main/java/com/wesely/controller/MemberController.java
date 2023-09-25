package com.wesely.controller;

import java.awt.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesely.service.MemberService;
import com.wesely.vo.CommVO;
import com.wesely.vo.MemberVO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	// 회원 가입 폼 처리
	@RequestMapping(value = "/join")
	public String join() {
		return "/member/join";
	}

	// 회원 가입 처리
	@GetMapping("/joinOk")
	public String joinOkGet() {
		return "redirect:/";
	}

	@PostMapping("/joinOk")
	public String joinOkPost(@ModelAttribute MemberVO memberVO) {
		log.info("joinOkPost({})호출", memberVO);
		if (memberVO != null) {
			// 서비스를 호출하여 저장을 수행한다.
			memberService.insert(memberVO);
		}
		return "redirect:/member/login";
	}

	// 아이디 중복확인
	@RequestMapping(value = "/idCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String idCheck(@RequestParam String userid) {
		log.info("{}의 idCheck 호출 : {}", this.getClass().getName(), userid);
		int count = memberService.idCheck(userid);
		log.info("{}의 idCheck 리턴 : {}", this.getClass().getName(), count);
		return count + "";
	}

	// 별명 중복확인
	@RequestMapping(value = "/nicknameCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String nicknameCheck(@RequestParam String nickname) {
		log.info("{}의 nicknameCheck 호출 : {}", this.getClass().getName(), nickname);
		int count = memberService.nicknameCheck(nickname);
		log.info("{}의 nicknameCheck 리턴 : {}", this.getClass().getName(), count);
		return count + "";
	}

	// 전화번호 중복확인
	@RequestMapping(value = "/phoneCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String phoneCheck(@RequestParam String phone) {
		log.info("{}의 phoneCheck 호출 : {}", this.getClass().getName(), phone);
		int count = memberService.phoneCheck(phone);
		log.info("{}의 phoneCheck 리턴 : {}", this.getClass().getName(), count);
		return count + "";
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

	// 로그인 처리하기
	@GetMapping(value = "/loginOk")
	public String loginOk(Model model) {
		return "redirect:/";
	}

	@PostMapping(value = "/loginOk")
	public String loginOkPost(@ModelAttribute MemberVO memberVO, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		if (memberVO != null) {
			// 서비스를 호출하여 로그인을 수행한다.
			MemberVO dbVO = memberService.login(memberVO);
			if (dbVO != null) { // 로그인에 성공했다면
				// 세션에 회원정보를 저장을 한다.
				String userid = request.getParameter("userid");
				String userpw = request.getParameter("userpw");

				session.setAttribute("mvo", dbVO);
				session.setAttribute("userid", userid);
				session.setAttribute("userpw", userpw);

				// 아이디 자동저장 처리
				Cookie cookie = null;
				if (memberVO.isSaveID()) { // 자동저장이라면
					cookie = new Cookie("userid", dbVO.getUserid());
					cookie.setMaxAge(60 * 60 * 24 * 7); // 초단위로 휴효기간 지정
				} else { // 자동저장이 아니라면
					cookie = new Cookie("userid", "");
					cookie.setMaxAge(0);
				}
				// 쿠키를 저장
				response.addCookie(cookie);

			} else {// 로그인에 실패했다면 로그인 폼으로 다시 보낸다.
				return "redirect:/member/login";
			}
		}
		return "redirect:/";
	}

	// 아이디 찾기 폼
	@GetMapping(value = "/findUserId")
	public String findUserId() {
		return "/member/findUserId";
	}

	// 아이디 찾기 실행
	@GetMapping(value = "/findUserIdOk")
	public String findUserIdOkGet() {
		return "redirect:/";
	}

	@PostMapping(value = "/findUserIdOk")
	public String findUserIdOkPost(@ModelAttribute MemberVO vo, Model model) {
		// 사용자 이름과 전화번호 받음
		MemberVO dbVO = memberService.findUserId(vo);
		if (dbVO == null) {
			return "redirect:/member/findUserId";
		}
		model.addAttribute("vo", dbVO);
		return "/member/viewUserId";
	}

	// 비밀번호 찾기 폼
	@GetMapping(value = "/findPassword")
	public String findPassword() {
		return "/member/findPassword";
	}

	// 비밀번호 찾기 실행
	@GetMapping(value = "/findPasswordOk")
	public String findPasswordGet() {
		return "redirect:/";
	}

	// 회원정보수정 폼
	@GetMapping(value = "/updateProfile")
	public String updateProfile() {
		return "/member/updateProfile";
	}

	@PostMapping(value = "/updateProfileOk")
	public String updateProfilePost(@ModelAttribute MemberVO vo, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		log.info("받은값 : {} ", vo);
		if (memberService.updateNickname(vo)) {
			MemberVO dbVO = memberService.findUserId(vo);
			// 세션정보 바꿈
			session.setAttribute("mvo", dbVO);
			return "redirect:/member/login";
		} else {
			return "redirect:/member/updateProfile";
		}
	}

	// 비밀번호변경 폼
	@GetMapping(value = "/updatePassword")
	public String updatePassword() {
		return "/member/updatePassword";
	}

	// 비밀번호변경 실행
	@PostMapping(value = "/updatePasswordOk")
	public String updatePasswordOk(@ModelAttribute MemberVO vo, @RequestParam String newPassword, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		if (memberService.updatePassword(vo, newPassword)) {
			return "redirect:/member/login";
		} else {
			return "redirect:/member/updatePassword";
		}
	}

	@Autowired
	private JavaMailSender javaMailSender;

	@PostMapping(value = "/findPasswordOk")
	public String findPasswordPost(@ModelAttribute MemberVO vo, Model model) throws MessagingException {
		MemberVO dbVO = memberService.findPassword(vo);
		if (dbVO == null) {
			// 일치하지 않는다면
			return "redirect:/member/findPassword";
		}
		// 일치하면
		// 메일을 발송하고
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("wesely@gmail.com");
		helper.setTo(dbVO.getEmail());
		helper.setSubject(dbVO.getUsername() + "님 비밀번호 안내입니다.");
		// 메일 내용 만들기
		StringBuffer sb = new StringBuffer();
		sb.append(dbVO.getUsername() + "님 비밀번호 안내입니다.<br>");
		sb.append(dbVO.getUsername() + "님의 임시 비밀번호는 " + dbVO.getPassword() + "입니다.<br>");
		helper.setText(sb.toString(), true);

		// 메일 발송
		javaMailSender.send(message);

		model.addAttribute("vo", dbVO);
		return "/member/viewPassword";
	}

	// 로그 아웃 처리
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		// 세션에 저장된 회원 정보를 지워버린다.
		session.removeAttribute("mvo");
		return "redirect:/";
	}

	
	@GetMapping(value = "/delete")
	public String delete() {
		return "/member/delete";
	}

	// 회원 탈퇴 처리
	@PostMapping(value = "/deleteOk")
	public String deleteOk(@ModelAttribute MemberVO vo, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		log.info("받은값 : {} ", vo);
		if (memberService.delete(vo)) {
			session.removeAttribute("mvo");
			return "redirect:/";
		} else {
			return "redirect:/member/delete";
		}
	}

	// 커뮤니티 목록보기
	@RequestMapping(value = { "/", "/list" })
	public String getList(@ModelAttribute CommVO cv, Model model) {

		return "comm/community";
	}

}
