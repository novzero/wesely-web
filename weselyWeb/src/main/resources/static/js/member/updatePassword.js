// ========================================================================================================
// 폼체크
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