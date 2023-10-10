// ========================================================================================================
// 비밀번호 변경페이지
// ========================================================================================================
$(function() {
	// 새 비밀번호 유효성검사 | 영문/숫자/특수문자를 포함하여 8~20자
	$("#newPassword").keyup(function() {
		var pw = $("#newPassword").val();
		var pw1 = $("#password").val();
		var reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
		if (pw.length >= 8 && pw.length <= 20) {
			if (!reg.test(pw)) {
				$("#newPwMessage").css('color', 'red').html("비밀번호는 영문/숫자/특수문자를 모두 포함해야합니다.");
			} else if (pw == pw1) {	// 현재 비밀번호와 동일한지 확인
				$("#newPwMessage").css('color', 'red').html("현재 비밀번호와 동일한 비밀번호가 입력되었습니다.");
			} else {
				$("#newPwMessage").css('color', 'green').html("사용가능한 비밀번호입니다.");
			}
		} else {
			$("#newPwMessage").css('color', 'red').html("비밀번호는 8자~20자로 작성해야합니다.");
		}
	});

	// 새 비밀번호 확인 검사
	$("#newPassword1").keyup(function() {
		var pw = $("#newPassword").val();
		var pw1 = $("#newPassword1").val();
		if (pw1 != null && pw1.length >= 1) {
			if (pw != pw1) {
				$("#newPwMessage1").css('color', 'red').html("비밀번호가 일치하지 않습니다.");
			} else {
				$("#newPwMessage1").css('color', 'green').html("비밀번호가 일치합니다.");
			}
		} else {
			$("#newPwMessage1").html(""); // 1자 미만이면 메세지 삭제
		}
	});

	// 비밀번호 보기/숨기기 아이콘 기능
	$(".pw-toggle").on("click", function() {
		var $passwordInput = $(this).prev("input");
		var currentType = $passwordInput.attr("type");

		if (currentType === "password") {
			$passwordInput.attr("type", "text");
			$(this).attr('src', '/images/view.png');
		} else {
			$passwordInput.attr("type", "password");
			$(this).attr('src', '/images/hide.png');
		}
	});
});


// ========================================================================================================
// 비밀번호변경 폼체크
// ========================================================================================================


function formCheck() {
	if ($("#password").val() == null || $("#password").val().trim().length == 0) {
		alert("현재 비밀번호는 반드시 입력해야 합니다.");
		$("#password").val("");
		$("#password").focus();
		return false;
	} else if ($("#newPassword").val() == null || $("#newPassword").val().trim().length == 0) {
		alert("새 비밀번호는 반드시 입력해야 합니다.");
		$("#newPassword").val("");
		$("#newPassword").focus();
		return false;
	} else if ($("#newPwMessage").css('color') != 'rgb(0, 128, 0)') {
		alert('새 비밀번호를 확인해주세요.');
		$("#newPassword").val("");
		$("#newPassword").focus();
		return false;
	} else if ($("#newPassword1").val() == null || $("#newPassword1").val().trim().length == 0) {
		alert("새 비밀번호는 반드시 입력해야 합니다.");
		$("#newPassword1").val("");
		$("#newPassword1").focus();
		return false;
	} else if ($("#newPwMessage1").css('color') != 'rgb(0, 128, 0)') {
		alert('새 비밀번호를 확인해주세요.');
		$("#newPassword1").val("");
		$("#newPassword1").focus();
		return false;
	} else if (window.confirm("비밀번호를 수정하시겠습니까? 다시 로그인해야합니다.")) {
		location.href = "/member/login";
	} else {
		return false;
	};
}


