
// ========================================================================================================
// 회원가입 유효성 검사
// ========================================================================================================

$(function() {
	// 아이디 유효성검사 | 6-12자의 영문 또는 숫자 구성
	$("#userid").on('input', function() {
		var value = $(this).val();
		var reg = /^[A-Za-z0-9]{6,12}$/;
		if (value != null && value.length >= 1) {
			if (!reg.test(value)) {
				$("#idMsg").html("아이디는 6~12자 이며, 영문 또는 숫자만 가능합니다.").css('color', 'red');
				$(this).focus();
				return false;
			} else {
				$("#idMsg").html(""); // 유효성검사를 만족한 경우 메시지 삭제
			}
		} else {
			$("#idMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	}).on('blur', function() {	// 아이디필드를 벗어났을 때 db요청을 통해 중복체크를 한다	
		var value = $(this).val();

		if (value.length >= 1) {
			// 아이디 중복확인, Ajax로 처리 | 아이디필드를 벗어나는 시점에 실행
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
		}
	});


	// 비밀번호 유효성검사 | 영문/숫자/특수문자를 포함하여 8~20자
	$("#password").on('input', function() {
		var pw = $("#password").val();
		var reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

		if (pw != null && pw.length >= 1) {
			if (pw.length >= 8 && pw.length <= 20) {
				if (!reg.test(pw)) {
					$("#pwMsg").css('color', 'red').html("비밀번호는 영문/숫자/특수문자를 모두 포함해야합니다.");
				} else {
					$("#pwMsg").css('color', 'green').html("사용 가능한 비밀번호입니다.");
				}
			} else {
				$("#pwMsg").css('color', 'red').html("비밀번호는 8자~20자로 작성해야합니다.");
			}
		} else {
			$("#pwMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	});

	// 비밀번호 확인 검사 | 입력할 때마다 검사
	$("#password1").on('input', function() {
		var pw = $("#password").val();
		var pw1 = $("#password1").val();

		if (pw !== "" && pw === pw1) {
			$("#pw1Msg").css('color', 'green').html("비밀번호가 일치합니다.");
		} else if (pw !== "" || pw1 !== "") {
			$("#pw1Msg").css('color', 'red').html("비밀번호가 일치하지 않습니다.");
		} else {
			$("#pw1Msg").html(""); // 둘 다 없을 경우 메시지 삭제
		}
	});


	// 비밀번호 및 비밀번호확인 보기/숨기기 구현
	$("#pwIcon, #pw1Icon").on("click", function() {
		var targetInput = this.id === "pwIcon" ? "#password" : "#password1";

		if ($(targetInput).attr("type") == "password") {
			$(targetInput).attr("type", "text");
			$(this).attr('src', '/images/view.png');
		} else {
			$(targetInput).attr("type", "password");
			$(this).attr('src', '/images/hide.png');
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
			} else {
				$("#nickMsg").html("");
			}
		} else {
			$("#nickMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	}).on('blur', function() {
		var value = $(this).val();
		if (value.length >= 1) {
			// 별명 중복 확인, ajax로 처리 | 닉네임필드를 벗어나는 시점에 실행
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
			} else {
				$("#emailMsg").html("");
			}
		} else {
			$("#emailMsg").html("");
		}
	}).on('blur', function() {
		var email = $("#email").val();
		if (email.length >= 1) {
			// 이메일 중복 확인, ajax로 처리 || 이메일 입력필드를 벗어나는 시점에 실행
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
		}
	});
	
});


// 전화번호검사
$('#phone').keyup(function() {
	var value = $(this).val();
	var regphone = /^01([0|1|6|7|8|9])([0-9]{4})([0-9]{4})$/;
	if (value != null && value.length >= 1) {
		if (!regphone.test(value)) {
			$("#phoneMsg").css('color', 'red').html("전화번호(11자리)가 올바르지 않습니다.");
			$(this).focus();
			return false;
		} else {
			$("#phoneMsg").html("");	// 유효성검사 조건을 만족할 경우 경고메세지 삭제
		}
	} else {
		$("#phoneMsg").html("");
	}
}).on('blur', function() {
	var value = $(this).val();
	if (value.length >= 1) {
		// 전화번호 중복 확인, ajax로 처리 || 전화번호 입력필드를 벗어나는 시점에 실행
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
	}
});

//==========================================================
// '사업자 등록번호' 입력하면 자동으로 다음필드로 이동
//==========================================================
$("#bNum1").on("keyup", function() {
	var value = $(this).val().match(/[0-9]/g)
	if (value != null && value.length == 3) {
		$("#bNum2").focus();
		$("#bNum2").select();
	}
});
$("#bNum2").on("keyup", function() {
	var value = $(this).val().match(/[0-9]/g)
	if (value != null && value.length == 2) {
		$("#bNum3").focus();
		$("#bNum3").select();
	}
});

//==========================================================

$(function() {
	// 사업자 인증 상태 변수 초기화
	var isBusinessConfirmed = false;

	disableBusinessInputs();
	updateJoinButtonState();

	$("#authority").change(function() {
		var selectedValue = $(this).val();	// 선택된 값 가져오기

		if (selectedValue === "비즈니스계정") {
			// 비즈니스계정이 선택되었을 때 사업자정보 입력필드 활성화
			enableBusinessInputs();
		} else {
			// 일반계정이 선택됐을 때 사업자정보 입력필드 비활성화
			disableBusinessInputs();
		}

		updateJoinButtonState(); // select 값 변경시 회원가입버튼 상태변경
	});

	// 비즈니스 정보 입력 필드 활성화 함수
	function enableBusinessInputs() {

		document.querySelector('.Bno').style.display = "block"
		document.querySelector('.Bname').style.display = "block"
		document.querySelector('.Store').style.display = "block"

		document.querySelector('#confirmBtn').style.display = "block"
	}

	// 비즈니스 정보 입력 필드 슴기기/보이기 함수
	function disableBusinessInputs() {

		// 해당 필드 초기화
		var bNumInputs = document.querySelectorAll('.bNumber');
		for (var i = 0; i < bNumInputs.length; i++) {
			bNumInputs[i].value = '';
		}
		document.querySelector('#bname').value = '';
		document.querySelector('#store').value = '';

		// 비즈니스 정보입력필드를 숨긴다.
		document.querySelector('.Bno').style.display = "none"
		document.querySelector('.Bname').style.display = "none"
		document.querySelector('.Store').style.display = "none"

		document.getElementById('confirmBtn').style.display = "none"
	}

	// 비즈니스 정보 입력 필드 비활성화 설정 함수
	function setBusinessInputsReadonly(isReadonly) {
		var bNumInputs = document.querySelectorAll('.bNumber');
		for (var i = 0; i < bNumInputs.length; i++) {
			bNumInputs[i].readOnly = isReadonly;
			bNumInputs[i].style.backgroundColor = isReadonly ? '#F2F2F2' : '';
			bNumInputs[i].style.color = isReadonly ? '#808080' : '';

		}
		var bnameField = document.querySelector('#bname');
		bnameField.readOnly = isReadonly;
		bnameField.style.backgroundColor = isReadonly ? '#F2F2F2' : '';
		bnameField.style.color = isReadonly ? '#808080' : '';

		var storeField = document.querySelector('#store');
		storeField.readOnly = isReadonly;
		storeField.style.backgroundColor = isReadonly ? '#F2F2F2' : '';
		storeField.style.color = isReadonly ? '#808080' : '';
	}


	// 사업자인증 버튼 클릭 이벤트 핸들러
	$("#confirmBtn").click(function() {
		var bno = $("#bNum1").val() + $("#bNum2").val() + $("#bNum3").val(); // 사업자등록번호 얻어오기
		var bname = $("#bname").val();
		var store = $("#store").val();

		if (!$("#bNum1").val() || !$("#bNum2").val() || !$("#bNum3").val() || !bname || !store) {  // 사업자정보입력필드에 공란이 있다면
			alert("사업자정보를 모두 입력해주세요");
			return;  // 함수 실행 중단
		}

		isBusinessConfirmed = false;
		updateJoinButtonState();

		$.ajax('/bnoCheck', {
			type: "GET",
			data: {
				"bno": bno
			},
			success: function(data) {
				if (data == 0) {
					// 사업자정보 유효성검사에 통과하였을 때
					alert("사업자정보가 인증되었습니다.");
					isBusinessConfirmed = true;
					updateJoinButtonState();
					setBusinessInputsReadonly(true);
				} else if (data == 1) {
					// 중복되는 사업자 번호가 존재할 때
					alert("이미 존재하는 사업자 번호입니다.");
					updateJoinButtonState();
					$("#bNum1").val("");
					$("#bNum1").focus();
				} else if (data == 2) {
					// 중복되는 사업자 번호가 존재할 때
					alert("사업자 번호가 유효하지 않습니다.");
					isBusinessConfirmed = false;
					updateJoinButtonState();
					$("#bNum1").val("");
					$("#bNum1").focus();
				} else if (data == -1) {
					// api 호출 실패했을 때
					alert("사업자 번호 유효성 체크 오류 발생");
					isBusinessConfirmed = false;
					updateJoinButtonState();
					$("#bNum1").val("");
					$("#bNum1").focus();
				} else {
					// 중복되는 사업자 번호가 존재할 때
					alert("서버 오류 발생");
					isBusinessConfirmed = false;
					updateJoinButtonState();
					$("#bNum1").val("");
					$("#bNum1").focus();
				}
			},
			error: function() {
				alert("에러!!!");
				isBusinessConfirmed = false;
				updateJoinButtonState();
			}
		})
	});

	// 회원가입 버튼 상태 변경 함수
	function updateJoinButtonState() {
		//  비즈니스 계정일 경우에만
		if ($("#authority").val() === '비즈니스계정') {
			// 회원가입버튼의 활성화 상태를 isBusinessConfirmed 값에 따라 결정
			if (isBusinessConfirmed) {  // 인증완료 -> 사업자인증버튼 숨기기
				$("#confirmBtn").css('display', 'none');
				$("#joinBtn").css('display', 'block');  // 인증완료 -> 회원가입버튼 보여주기
			} else {
				$("#confirmBtn").css('display', 'block');
				$("#joinBtn").css('display', 'none');  // 인증안됨 -> 버튼 숨기기
			}

		} else {
			// 일반 계정일 경우, 회원가입버튼은 항상 보여짐 
			$("#joinBtn").css('display', 'block');
		}
	}
})



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
	}

}