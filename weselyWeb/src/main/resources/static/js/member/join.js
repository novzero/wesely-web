
// ========================================================================================================
// 회원가입 유효성 검사
// ========================================================================================================

$(function() {
	$("#authority").on("blur", function() {
		var authority = $(this).val();

		if (authority != null) {
			$("#authMsg").html("");
		} else {
			$("#authMsg	").html("&#9888; 잘못된 계정유형입니다. 다시 선택해주세요.").css('color', 'red');
			return false;
		}
	});

	// 아이디 유효성검사 | 6-12자의 영문 또는 숫자 구성
	$("#userid").on('blur', function() {
		var value = $(this).val();
		var reg = /^[A-Za-z0-9]{6,12}$/;

		// 아이디 입력필드에 1글자 이상 입력되었을 경우
		if (value != null && value.length >= 1) {
			// 아이디조건을 만족하지 않는 경우
			if (!reg.test(value)) {
				$("#idMsg").html("&#9888; 잘못된 아이디를 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
				return false;
			} else {	// 아이디조건을 만족했다면
				// 아이디 중복확인, Ajax로 처리
				$.ajax('idCheck', {
					type: "GET",
					data: {
						"userid": value
					},
					success: function(data) {
						if (data * 1 >= 1) {
							$("#idMsg").html("&#9888; 이미 존재하는 아이디입니다.").css('color', 'red');
						} else {
							$("#idMsg").html("&#10004; 사용가능한 아이디입니다.").css('color', 'green');
						}
					},
					error: function() {
						alert("에러!!!");
					}
				});
			}
		} else {
			$("#idMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	});


	// 비밀번호 유효성검사 | 영문/숫자/특수문자를 포함하여 8~20자	
	$("#password").on('blur', function() {
		var pw = $("#password").val();
		var reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;

		// 비밀번호가 1자 이상 입력되었을 경우
		if (pw != null && pw.length >= 1) {
			if (!reg.test(pw)) {
				$("#pwMsg").css('color', 'red').html("&#9888; 잘못된 비밀번호를 입력하였습니다. 다시 입력해주세요.");
			} else {
				$("#pwMsg").css('color', 'green').html("&#10004; 사용가능한 비밀번호입니다.");
			}
		} else {
			$("#pwMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	});

	// 비밀번호 확인 검사
	$("#password1").on('blur', function() {
		var pw = $("#password").val();
		var pw1 = $("#password1").val();
		var reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;

		// 비밀번호 재입력필드에 1자 이상 입력되었을 경우
		if (pw1 != null && pw1.length >= 1) {
			// 비밀번호 조건에 만족하지 않을 경우
			if (!reg.test(pw1)) {
				$("#pw1Msg").css('color', 'red').html("&#9888; 잘못된 비밀번호를 입력하였습니다. 다시 입력해주세요.");
			} else {	// 비밀번호 조건에 만족한다면
				if (pw !== "" && pw === pw1) {
					$("#pw1Msg").css('color', 'green').html("&#10004; 비밀번호가 일치합니다.");
				} else {
					$("#pw1Msg").css('color', 'red').html("&#9888; 잘못된 비밀번호를 입력하였습니다. 다시 입력해주세요.");
				}
			}
		} else {
			$("#pw1Msg").html(""); // 1자 미만이면 메세지 삭제
		}
	});

	// 비밀번호 및 비밀번호확인 보기/숨기기 구현 | 눈 아이콘
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

	// 사용자 이름
	$("#username").on("blur", function() {
		var username = $(this).val();

		if (username != null) {
			$("#nameMsg").html("");
		} else {
			$("#nameMsg	").html("&#9888; 사용자 이름을 입력해주세요.").css('color', 'red');
			return false;
		}
	});

	// 닉네임 검사 | 2글자 이상 5글자 이하로 작성
	$("#nickname").on("blur", function() {
		var value = $(this).val();
		var reg = /^[A-Za-z가-힣]{2,5}$/;

		// 닉네임 입력필드에 1자 이상 입력되었을 경우
		if (value != null && value.length >= 1) {
			// 닉네임 조건을 만족하지 않았을 경우
			if (!reg.test(value)) {
				$("#nickMsg").css('color', 'red').html("&#9888; 잘못된 닉네임을 입력하였습니다. 다시 입력해주세요.");
				return false;
			} else {	// 닉네임 조건을 만족했다면 별명 중복 확인, ajax로 처리 
				$.ajax('nicknameCheck', {
					type: "GET",
					data: {
						"nickname": value
					},
					success: function(data) {
						if (data * 1 >= 1) {
							$("#nickMsg").html("&#9888; 이미 존재하는 닉네임입니다.").css('color', 'red');
						} else {
							$("#nickMsg").html("&#10004; 사용가능한 닉네임입니다.").css('color', 'green');
						}
					},
					error: function() {
						alert("에러!!!");
					}
				});
			}
		} else {
			$("#nickMsg").html(""); // 1자 미만이면 메세지 삭제
		}
	});

	// 이메일 유효성검사
	$('#email').on("blur", function() {
		var email = $("#email").val();
		var regExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;

		// 이메일 입력필드에 1자 이상 입력되었을 경우
		if (email != null && email.length >= 1) {
			// 이메일 형식에 맞지 않는 경우
			if (!regExp.test(email)) {
				$("#emailMsg").css('color', 'red').html("&#9888; 잘못된 이메일 주소를 입력하였습니다. 다시 입력해주세요.");
				return false;
			} else {	// 이메일 형식이 올바르다면 중복체크 진행
				$.ajax('emailCheck', {
					type: "GET",
					data: {
						"email": email
					},
					success: function(data) {
						if (data * 1 >= 1) {
							$("#emailMsg").html("&#9888; 해당 이메일로 이미 가입된 계정이 있습니다.").css('color', 'red');
						} else {
							$("#emailMsg").html("&#10004; 사용가능한 이메일입니다.").css('color', 'green');
						}
					},
					error: function() {
						alert("에러!!!");
					}
				});
			}
		} else {
			$("#emailMsg").html("");
		}
	});


	// 전화번호검사
	$('#phone').on("blur", function() {
		var value = $(this).val();
		var regphone = /^01([0|1|6|7|8|9])([0-9]{4})([0-9]{4})$/;

		// 전화번호가 1글자 이상 입력되었을 경우
		if (value != null && value.length >= 1) {
			if (!regphone.test(value)) {
				$("#phoneMsg").css('color', 'red').html("&#9888; 잘못된 전화번호를 입력하였습니다. 다시 입력해주세요.");
				return false;
			} else {
				// 입력조건을 만족했다면 중복체크 | ajax
				$.ajax('phoneCheck', {
					type: "GET",
					data: {
						"phone": value
					},
					success: function(data) {
						if (data * 1 >= 1) {
							$("#phoneMsg").html("&#9888; 이미 존재하는 전화번호입니다.").css('color', 'red');
						} else {
							$("#phoneMsg").html("&#10004; 사용가능한 전화번호입니다.").css('color', 'green');
						}
					},
					error: function() {
						alert("에러!!!");
					}
				});
			}
		} else {
			$("#phoneMsg").html("");
		}
	});

});

//==========================================================
// 사업자정보 필드 관련 js

$(function() {
	//==========================================================
	// '사업자 등록번호' 입력하면 자동으로 다음필드로 이동
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

	$("#bNum1").on("blur", function() {
		var value = $(this).val();

		// 사업자번호 첫번째 입력필드에 1글자 이상 입력되었을 경우
		if (value !== null && value.length >= 1) {
			$("#bnoMsg").html("");
		}
	});

	$("#bname").on("blur", function() {
		var value = $(this).val();

		// 대표자 성명이 1글자 이상 입력되었을 경우
		if (value !== null && value.length >= 1) {
			$("#bnameMsg").html("");
		}
	});

	$("#store").on("blur", function() {
		var value = $(this).val();

		// 상호명이 1글자 이상 입력되었을 경우
		if (value !== null && value.length >= 1) {
			$("#storeMsg").html("");
		}
	});
	//==========================================================


	// 사업자 인증 상태 변수 초기화
	var isBusinessConfirmed = false;

	disableBusinessInputs();
	updateJoinButtonState();

	$("#authority").change(function() {
		var selectedValue = $(this).val();	// 선택된 값 가져오기

		if (selectedValue == "business") {
			// 비즈니스계정이 선택되었을 때 사업자정보 입력필드 활성화
			enableBusinessInputs();
		} else {
			// 비즈니스 계정이 선택되지 않았을 때
			disableBusinessInputs();	// 사업자정보 입력필드 비활성화
		}

		updateJoinButtonState(); // select 값 변경시 회원가입버튼 상태변경
	});

	// 회원가입 버튼 상태 변경 함수
	function updateJoinButtonState() {
		//  비즈니스 계정일 경우에만
		if ($("#authority").val() === 'business') {
			// 회원가입버튼의 활성화 상태를 isBusinessConfirmed 값에 따라 결정
			if (isBusinessConfirmed) {  // 인증완료 -> 사업자인증버튼 숨기기
				$("#bnoMsg").html("");
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
	};

	// 비즈니스 정보 입력 필드 활성화 함수
	function enableBusinessInputs() {
		document.querySelector('.Bno').style.display = "block"
		document.querySelector('.Bname').style.display = "block"
		document.querySelector('.Store').style.display = "block"

		document.querySelector('#confirmBtn').style.display = "block";
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

		// 사업자 정보 입력필드를 숨긴다.
		document.querySelector(".Bno").style.display = "none";
		document.querySelector(".Bname").style.display = "none";
		document.querySelector(".Store").style.display = "none";

		document.getElementById('confirmBtn').style.display = "none";
		setBusinessInputsReadonly(false);
		isBusinessConfirmed = false;
		$("#storeMsg").html("");
		
	}

	// 비즈니스 정보 입력 필드 비활성화 설정 함수
	function setBusinessInputsReadonly(isReadonly) {
		var inputs = document.querySelectorAll('.bNumber, #bname, #store');
		inputs.forEach(function(input) {
			input.readOnly = isReadonly;
			input.style.backgroundColor = isReadonly ? '#F2F2F2' : '';
			input.style.color = isReadonly ? '#808080' : '';
			input.style.pointerEvents = isReadonly ? 'none' : '';
		})
	};


	// 사업자인증 버튼 클릭 이벤트 핸들러
	$("#confirmBtn").click(function() {
		var bno = $("#bNum1").val() + $("#bNum2").val() + $("#bNum3").val(); // 사업자등록번호 얻어오기
		var bname = $("#bname").val();
		var store = $("#store").val();

		if (!$("#bNum1").val() || !$("#bNum2").val() || !$("#bNum3").val()) {  // 사업자정보입력필드에 공란이 있다면
			$("#bnoMsg").html("&#9888; 잘못된 사업자번호를 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
			$("#bNum1").focus();
			return false;  // 함수 실행 중단
		} else if (!bname) {
			$("#bnameMsg").html("&#9888; 대표자성함을 입력해주세요.").css('color', 'red');
			$("#bname").focus();
			return false;
		} else if (!store) {
			$("#storeMsg").html("&#9888; 상호명을 입력해주세요.").css('color', 'red');
			$("#store").focus();
			return false;
		} else {
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
						//alert("사업자정보가 인증되었습니다.");
						$("#storeMsg").html("&#10004; 사업자 정보가 인증되었습니다.").css('color', 'green');
						isBusinessConfirmed = true;
						updateJoinButtonState();
						setBusinessInputsReadonly(true);
					} else if (data == 1) {
						// 사업자번호가 중복일 경우
						isBusinessConfirmed = false;
						updateJoinButtonState();
						$("#bNum1").focus();
						$("#bnoMsg").html("&#9888; 해당 사업자번호로 가입된 계정이 있습니다. 다시 입력해주세요.").css('color', 'red');
					} else if (data == 2) {
						// 등록되지 않은 사업자 번호일 
						isBusinessConfirmed = false;
						updateJoinButtonState();
						$("#bNum1").focus();
						$("#bnoMsg").html("&#9888; 잘못된 사업자번호입니다. 다시 입력해주세요.").css('color', 'red');
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
		}


	});



});

// ========================================================================================================
// 폼체크
// ========================================================================================================
function formCheck() {

	// 아이디 폼체크
	if ($("#authority").val() == null) {
		$("#authority").focus();
		$("#authMsg").html("&#9888; 잘못된 계정유형입니다. 다시 선택해주세요.").css('color', 'red');
		return false;
	} else if ($("#userid").val() == null || $("#userid").val().trim().length == 0 || $("#idMsg").css('color') != 'rgb(0, 128, 0)') {
		$("#userid").focus();
		$("#idMsg").html("&#9888; 잘못된 아이디를 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
		return false;
	} else if ($("#password").val() == null || $("#password").val().trim().length == 0 || $("#pwMsg").css('color') != 'rgb(0, 128, 0)') {
		$("#password").focus();
		$("#pwMsg").html("&#9888; 잘못된 비밀번호룰 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
		return false;
	} else if ($("#password1").val() == null || $("#password1").val().trim().length == 0 || $("#pw1Msg").css('color') != 'rgb(0, 128, 0)') {
		$("#password1").focus();
		$("#pw1Msg").html("&#9888; 잘못된 비밀번호룰 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
		return false;
	} else if ($("#username").val() == null || $("#username").val().trim().length == 0) {
		$("#username").focus();
		$("#nameMsg").html("&#9888; 잘못된 이름을 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
		return false;
	} else if ($("#nickMsg").css('color') != 'rgb(255,0,0)' && $("#nickMsg").text().trim().length != 0) {
		$("#nickname").focus();
		$("#nickMsg").html("&#9888; 잘못된 닉네임을 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
		return false;
	} else if ($("#email").val() == null || $("#email").val().trim().length == 0 || $("#emailMsg").css('color') != 'rgb(0, 128, 0)') {
		$("#email").focus();
		$("#emailMsg").html("&#9888; 잘못된 이메일을 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
		return false;
	} else if ($("#phone").val() == null || $("#phone").val().trim().length == 0 || $("#phoneMsg").css('color') != 'rgb(0, 128, 0)') {
		$("#phone").focus();
		$("#phoneMsg").html("&#9888; 잘못된 전화번호를 입력하였습니다. 다시 입력해주세요.").css('color', 'red');
		return false;
	} else if (window.confirm("회원가입을 완료하시겠습니까?")) {
		location.href = "/member/joinOk";
	} else {
		return false;
	}

}