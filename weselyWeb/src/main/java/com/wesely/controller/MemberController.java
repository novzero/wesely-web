package com.wesely.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

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
	public String joinOkPost(@ModelAttribute MemberVO memberVO, Model model) {
		log.info("joinOkPost({})호출", memberVO);
		if (memberVO != null) {
			// 닉네임이 비어있을 경우
			if (memberVO.getNickname().equals("")) {
				// 닉네임 자리에 아이디를 넣어준다.
				memberVO.setNickname(memberVO.getUserid());
			}
			// 서비스를 호출하여 저장을 수행한다.
			memberService.insert(memberVO);
		}
		model.addAttribute("username", memberVO.getUsername());
		return "member/joinComplete";
	}

	// 회원가입완료 처리
	@GetMapping("/joinComplete")
	public String joinCompleteGet(MemberVO memberVO, Model model) {
		return "member/joinComplete";
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

	// 이메일 중복확인
	@RequestMapping(value = "/emailCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String emailCheck(@RequestParam String email) {
		log.info("{}의 emailCheck 호출 : {}", this.getClass().getName(), email);
		int count = memberService.emailCheck(email);
		log.info("{}의 emailCheck 리턴 : {}", this.getClass().getName(), count);
		return count + "";
	}

	// 회원약관
	@GetMapping(value = "/agreement")
	public String agreement() {
		return "/member/agreement";
	}

	// 로그인 폼 처리하기
	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		// 로그인 폼으로 포워딩 한다.
		return "/member/login";
	}

	// 로그인 처리하기
	@GetMapping(value = "/loginOk")
	public String loginOk(Model model) {
		return "redirect:/";
	}

	@PostMapping(value = "/loginOk")
	public String loginOkPost(@ModelAttribute MemberVO memberVO, HttpServletResponse response, HttpSession session,
			Model model) {
		if (memberVO != null) {
			// 서비스를 호출하여 로그인을 수행한다.
			MemberVO dbVO = memberService.login(memberVO);
			if (dbVO != null) { // 로그인에 성공했다면
				// 세션에 회원정보를 저장을 한다.
				session.setAttribute("mvo", dbVO);

				// 이전 페이지 정보 가져오기
				Object prevPageObj = session.getAttribute("prevPage");

				if (prevPageObj != null) { // 만약 이전 페이지 정보가 있다면
					session.removeAttribute("prevPage"); // 세션에서 제거하고
					return "redirect:" + prevPageObj.toString(); // 그 페이지로 리다이렉트합니다.
				}

			} else {// 로그인에 실패했다면 로그인 폼으로 다시 보낸다.
				return "/member/login";
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

	@Autowired
	private JavaMailSender javaMailSender;

	@PostMapping(value = "/findPasswordOk")
	public String findPasswordPost(@ModelAttribute MemberVO vo, Model model) throws MessagingException {
		MemberVO dbVO = memberService.findPassword(vo);
		if (dbVO == null) {
			// 일치하지 않는다면
			return "redirect:/member/findPassword";
		}
		// 일치하면 메일을 발송한다.
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("sksmsdlcodud@gmail.com"); // 보내는 사람
		helper.setTo(dbVO.getEmail()); // 받는 사람
		helper.setSubject(dbVO.getUsername() + "님 비밀번호 안내입니다."); // 메일제목
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

	// 회원정보수정 폼
	@GetMapping(value = "/updateProfile")
	public String updateProfile() {
		return "/member/updateProfile";
	}

	@PostMapping(value = "/updateProfileOk")
	public String updateProfilePost(@ModelAttribute MemberVO vo, HttpSession session) {
		log.info("받은값 : {} ", vo);
		if (memberService.updateNickname(vo)) {
			// 닉네임 업데이트가 성공했다면
			// 업데이트된 회원 정보 가져오기
			MemberVO dbVO = memberService.findUserById(vo.getUserid());
			log.info("닉네임이 변경된 회원 정보 : {}", dbVO);

			// 세션정보 바꿈
			session.setAttribute("mvo", dbVO);

			return "redirect:/member/updateProfile";
		} else {
			return "redirect:/member/updateProfile";
		}
	}

	// 프로필 사진 등록
	@RequestMapping("/member/photoView.do")
	public String getProfile(HttpSession session, HttpServletRequest request, Model model) {
		// 로그인한 회원 정보 세션에서 가져오기
		MemberVO dbVO = (MemberVO) session.getAttribute("dbVO");
		if (dbVO != null) {

		}
		return "imageView";
	}

	@PostMapping("/upload-image")
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile imageFile) {
		try {
			// 멤버 정보 객체 생성
			MemberVO memberVO = new MemberVO();
			memberVO.setImageFile(imageFile);

			// 이미지 저장 로직 실행
			memberService.saveImage(memberVO);

			// 필요한 후속 작업 수행

			return ResponseEntity.ok("이미지 업로드 성공");

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 실패");
		}
	}

	// 비밀번호변경 폼
	@GetMapping(value = "/updatePassword")
	public String updatePassword() {
		return "/member/updatePassword";
	}

	// 비밀번호변경 실행
	@PostMapping(value = "/updatePasswordOk")
	public String updatePasswordOk(@ModelAttribute MemberVO vo, @RequestParam String newPassword) {
		if (memberService.updatePassword(vo, newPassword)) {
			return "redirect:/member/login";
		} else {
			return "redirect:/member/updatePassword";
		}
	}

	// 로그 아웃 처리
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		// 세션에 저장된 회원 정보를 지워버린다.
		session.removeAttribute("mvo");
		return "redirect:/";
	}

	// 회원탈퇴폼
	@GetMapping(value = "/delete")
	public String delete() {
		return "/member/delete";
	}

	// 회원 탈퇴 처리
	@PostMapping(value = "/deleteOk")
	public String deleteOk(@ModelAttribute MemberVO vo, HttpSession session) {
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
