
function formCheck() {
	var useridField = document.getElementById("userid");
	var userid = useridField.value.trim();

	var passwordField = document.getElementById("password");
	var password = passwordField.value.trim();

	if (!userid || userid.indexOf(" ") > -1) {
		// 입력값이 없거나 공백이 존재할 경우 애니메이션 적용
		shakeAndHighlight(useridField);
		$("#userid").val("").focus();
		return false;
	}

	if (!password || password.indexOf(" ") > -1) {
		// 입력값이 없거나 공백이 존재할 경우 애니메이션 적용
		shakeAndHighlight(passwordField);
		$("#password").val("").focus();
		return false;
	}

	// AJAX 요청으로 아이디와 비밀번호의 일치 여부 확인
	axios.post("/member/loginOk", { userid: userid, password: password })
		.then(function(response) {
			if (response.data.success === true) {
				// 로그인 성공한 경우

				// 추가적인 유효성 검사 또는 서버로의 요청 처리

				// 폼 제출 허용
				return true;
			} else {
				// 로그인 실패한 경우
				shakeAndHighlight(useridField);
				shakeAndHighlight(passwordField);

				setTimeout(function() {
					alert("아이디 또는 비밀번호가 일치하지 않습니다.");
					$("#userid").val("").focus();
					$("#password").val("");
				}, 200); // 애니메이션이 재생되기 위해 약간의 딜레이 추가

				return false;
			}
		})
		.catch(function(error) {
			console.error(error);
			alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
			return false;
		});
};

function shakeAndHighlight(element) {
	element.classList.add("invalid");

	var originalBorderColor = getComputedStyle(element).borderColor;

	var animationKeyframes = [
		{ transform: 'translateX(-5px)' },
		{ transform: 'translateX(5px)' },
		{ transform: 'translateX(-5px)' },
		{ transform: 'translateX(5px)' },
		{ transform: 'translateX(-5px)' },
		{ transform: 'translateX(0)' }
	];

	var animationOptions = {
		duration: 200,
		easing: "ease-in-out",
	};

	element.animate(animationKeyframes, animationOptions);

	setTimeout(function() {
		element.style.borderColor = "red";
	}, 0);

	// 입력창에 텍스트가 변경될 때마다 원래 색으로 복원
	element.addEventListener("input", function() {
		element.classList.remove("invalid");
		element.style.transform = "none";
		element.style.borderColor = originalBorderColor;
	});
};