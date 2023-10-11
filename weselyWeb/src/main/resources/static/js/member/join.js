// ========================================================================================================
// 회원가입 유효성 검사
// ========================================================================================================

$(function() {
	// 아이디 유효성검사 | 6-12자의 영문 또는 숫자 구성
	$("#userid").keyup(function() {
		var value = $(this).val();
		var reg = /^[A-Za-z0-9]{6,12}$/;
		if (value != null && value.length >= 1) {
			if (!reg.test(value)) {
				$("#idMsg").html("아이디는 6~12자 이며, 영문 또는 숫자만 가능합니다.").css('color', 'red');
				$(this).focus();
				return false;
			}
			// 아이디 중복확인 : Ajax로 처리
			$.ajax('idCheck', {
				type: "GET",
				data: {
					"userid": value
				},
				success: function(data) {
					if (data * 1 >= 1) {
						$("#idMsg").html("이미 존재하는 아이디입니다.").css('color', 'red');
					} else {
						$("#idMsg").html("사용가능한 아이디입니다.").css('color', 'green');
					}
				},
				error: function() {
					alert("에러!!!");
				}
			});
		} else {
			$("#idMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	});


	// 비밀번호 유효성검사 | 영문/숫자/특수문자를 포함하여 8~20자
	$("#password").keyup(function() {
		var pw = $("#password").val();
		var reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
		if (pw != null && pw.length >= 1) {
			if (pw.length >= 8 && pw.length <= 20) {
				if (!reg.test(pw)) {
					$("#pwMsg").css('color', 'red').html("비밀번호는 영문/숫자/특수문자를 모두 포함해야합니다.");
				} else {
					$("#pwMsg").css('color', 'green').html("사용가능한 비밀번호입니다.");
				}
			} else {
				$("#pwMsg").css('color', 'red').html("비밀번호는 8자~20자로 작성해야합니다.");
			}
		} else {
			$("#pwMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	});

	// 비밀번호 확인 검사
	$("#password1").keyup(function() {
		var pw = $("#password").val();
		var pw1 = $("#password1").val();
		if (pw1 != null && pw1.length >= 1) {
			if (pw1 != pw) {
				$("#pw1Msg").css('color', 'red').html("비밀번호가 일치하지 않습니다.");
			} else {
				$("#pw1Msg").css('color', 'green').html("비밀번호가 일치합니다.");
			}
		} else {
			$("#pw1Msg").html(""); // 1자 미만이면 메세지 삭제
		}
	});


	// 비밀번호 보기/숨기기 구현
	$("#pwIcon").on("click", function() {
		if ($("#password").attr("type") == "password") {
			$("#password").attr("type", "text");
			$($(this)).attr('src', '/images/view.png');
		} else {
			$("#password").attr("type", "password");
			$($(this)).attr('src', '/images/hide.png');
		}
	});

	// 비밀번호확인 보기/숨기기 구현
	$("#pw1Icon").on("click", function() {
		if ($("#password1").attr("type") == "password") {
			$("#password1").attr("type", "text");
			$($(this)).attr('src', '/images/view.png');
		} else {
			$("#password1").attr("type", "password");
			$($(this)).attr('src', '/images/hide.png');
		}
	});



	// 닉네임 검사 | 2글자 이상 5글자 이하로 작성
	$("#nickname").keyup(function() {
		var value = $(this).val();
		var reg = /^[A-Za-z가-힣]{2,5}$/;
		if (value != null && value.length >= 1) {
			if (!reg.test(value)) {
				$("#nickMsg").css('color', 'red').html("닉네임은 2~5자이며(공백X) 한글 또는 영문만 가능합니다.");
				$(this).focus();
				return false;
			}
			// 별명 중복 확인, ajax로 처리
			$.ajax('nicknameCheck', {
				type: "GET",
				data: {
					"nickname": value
				},
				success: function(data) {
					if (data * 1 >= 1) {
						$("#nickMsg").html("이미 존재하는 닉네임입니다.").css('color', 'red');
					} else {
						$("#nickMsg").html("사용가능한 닉네임입니다.").css('color', 'green');
					}
				},
				error: function() {
					alert("에러!!!");
				}
			});
		} else {
			$("#nickMsg").html(""); // 1자 미만이면 메세지 삭제
		}

	});


	// 이메일 유효성검사
	$('#email').keyup(function() {
		var email = $("#email").val();
		var regExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
		if (email != null && email.length >= 1) {
			if (!regExp.test(email)) {
				$("#emailMsg").css('color', 'red').html("이메일 형식에 맞지않습니다.");
				return false;
			}

			// 이메일 중복 확인, ajax로 처리
			$.ajax('emailCheck', {
				type: "GET",
				data: {
					"email": email
				},
				success: function(data) {
					if (data * 1 >= 1) {
						$("#emailMsg").html("해당 이메일로 이미 가입된 계정이 있습니다.").css('color', 'red');
					} else {
						$("#emailMsg").html("사용가능한 이메일입니다.").css('color', 'green');
					}
				},
				error: function() {
					alert("에러!!!");
				}
			});
		} else {
			$("#emailMsg").html("");
		}
	});


	// 전화번호검사
	$('#phone').keyup(function() {
		var value = $(this).val();
		var regphone = /^01([0|1|6|7|8|9])([0-9]{4})([0-9]{4})$/;
		if (!regphone.test(value)) {
			$("#phoneMsg").css('color', 'red').html("전화번호(11자리)가 올바르지 않습니다.");
			$(this).focus();
			return false;
		}
		// 전화번호 중복 확인, ajax로 처리
		$.ajax('phoneCheck', {
			type: "GET",
			data: {
				"phone": value
			},
			success: function(data) {
				if (data * 1 >= 1) {
					$("#phoneMsg").html("이미 존재하는 전화번호입니다.").css('color', 'red');
				} else {
					$("#phoneMsg").html("사용가능한 전화번호입니다.").css('color', 'green');
				}
			},
			error: function() {
				alert("에러!!!");
			}
		});
	});

});

//==========================================================
// select 값 읽어오기

const showValue = (target) => {
	const value = target.value;
	// document.querySelector(`.abc`).innerHTML = `value: ${value}`;
	const businessNo = document.querySelector(".Bno");	// 사업자 번호
	const bname = document.querySelector(".Bname");		// 대표자 성명
	const store = document.querySelector(".Store"); 		// 상호명
	const cofirm = document.querySelector("#cofirmBtn");	// 사업자 인증 버튼
	const join = document.querySelector(".joinBtn");

	if (value == '비즈니스계정') {	// select가 비즈니스 계정이 선택됐을 때
		// 사업자번호, 대표자성명, 상호명 input 보여라
		businessNo.style.display = "block"
		bname.style.display = "block"
		store.style.display = "block"
		cofirm.style.display = "none"
		join.disabled = "true"
	} else {
		businessNo.style.display = "none"
		bname.style.display = "none"
		store.style.display = "none"
		cofirm.style.display = "none"
	}
	
	
}

//==========================================================
// '사업자 등록번호' 맨앞 3자리 입력 후 다음으로 이동
//==========================================================
$("#bNum1").on("keyup", function() {
	var value = $(this).val().match(/[0-9]/g)
	if (value != null && value.length == 3) {
		$("#bNum2").focus();
		$("#bNum2").select();
	}
});

//==========================================================
// '사업자 등록번호' 가운데 2자리 입력 후 다음으로 이동
//==========================================================
$("#bNum2").on("keyup", function() {
	var value = $(this).val().match(/[0-9]/g)
	if (value != null && value.length == 2) {
		$("#bNum3").focus();
		$("#bNum3").select();
	}
});

//==========================================================
//==========================================================
// 사업자 인증 체크
var bnock = 0;
$(function() {
	$("#cofirmBtn").click(function() {

		if ($("#bNum1").val() == null || $("#bNum1").val().trim().length == 0) {
			alert("사업자 등록번호(10자리)는 반드시 입력해야 합니다.");
			$("#bNum1").val("");
			$("#bNum1").focus();
			return false;
		} else if ($("#bNum2").val() == null || $("#bNum2").val().trim().length == 0) {
			alert("사업자 등록번호(10자리)는 반드시 입력해야합니다.");
			$("#bNum2").val("");
			$("#bNum2").focus();
			return false;
		} else if ($("#bNum3").val() == null || $("#bNum3").val().trim().length == 0) {
			alert("사업자 등록번호(10자리)는 반드시 입력해야합니다.");
			$("#bNum3").val("");
			$("#bNum3").focus();
			return false;
		} else if ($("#bname").val() == null || $("#bname").val().trim().length == 0) {
			alert("대표자성명은 반드시 입력해야 합니다.");
			$("#bname").val("");
			$("#bname").focus();
			return false;
		} else if ($("#store").val() == null || $("#store").val().trim().length == 0) {
			alert("상호명은 반드시 입력해야 합니다.");
			$("#store").val("");
			$("#store").focus();
			return false;
		} else {
			const bno = $("#bNum1").val() + $("#bNum2").val() + $("#bNum3").val(); // 사업자등록번호 얻어오기
			
			$.ajax('/bnoCheck', {
				type: "GET",
				data: {
					"bno": bno
				},
				success: function(data) {
					if (data * 1 >= 1) {
						alert("이미 존재하는 사업자 번호입니다.");
					} else {
						alert("가능가능");
						bnock = 1;
					}
				},
				error: function() {
					alert("에러!!!");
				}
			});
		}
	})
});

// ========================================================================================================
// 폼체크
// ========================================================================================================
function formCheck() {

	// 아이디 폼체크
	if ($("#userid").val() == null || $("#userid").val().trim().length == 0) {
		alert("아이디는 반드시 입력해야 합니다.");
		$("#userid").val("");
		$("#userid").focus();
		return false;
	} else if ($("#idMsg").css('color') != 'rgb(0, 128, 0)') {
		alert("아이디를 확인해주세요.");
		$("#userid").val("");
		$("#userid").focus();
		return false;
	} else if ($("#password").val() == null || $("#password").val().trim().length == 0) {
		alert("비밀번호는 반드시 입력해야 합니다.");
		$("#password").val("");
		$("#password").focus();
		return false;
	} else if ($("#pwMsg").css('color') != 'rgb(0, 128, 0)') {
		alert('비밀번호를 확인해주세요.');
		$("#password").val("");
		$("#password").focus();
		return false;
	} else if ($("#password1").val() == null || $("#password1").val().trim().length == 0) {
		alert("비밀번호 확인은 반드시 입력해야 합니다.");
		$("#password1").val("");
		$("#password1").focus();
		return false;
	} else if ($("#pw1Msg").css('color') != 'rgb(0, 128, 0)') {
		alert('비밀번호를 확인해주세요.');
		$("#password1").val("");
		$("#password1").focus();
		return false;
	} else if ($("#username").val() == null || $("#username").val().trim().length == 0) {
		alert("사용자 이름은 반드시 입력해야 합니다.");
		$("#username").val("");
		$("#username").focus();
		return false;
	} else if ($("#authority").val() == null) {
		alert("계정 유형을 선택해주세요.");
		$("#authority").focus();
		return false;
	} else if ($("#email").val() == null || $("#email").val().trim().length == 0) {
		alert("이메일 주소는 반드시 입력해야 합니다.");
		$("#email").val("");
		$("#email").focus();
		return false;
	} else if ($("#emailMsg").css('color') != 'rgb(0, 128, 0)') {
		alert('이메일 주소를 다시 확인해주세요.');
		$("#email").val("");
		$("#email").focus();
		return false;
	} else if ($("#phone").val() == null || $("#phone").val().trim().length == 0) {
		alert("전화번호는 반드시 입력해야 합니다.");
		$("#phone").val("");
		$("#phone").focus();
		return false;
	} else if ($("#phoneMsg").css('color') != 'rgb(0, 128, 0)') {
		alert('전화번호를 다시 확인해주세요.');
		$("#phone").val("");
		$("#phone").focus();
		return false;
	} else if (window.confirm("회원가입을 완료하시겠습니까?")) {
		location.href = "/member/joinOk";
	} else {
		return false;
	};


}




