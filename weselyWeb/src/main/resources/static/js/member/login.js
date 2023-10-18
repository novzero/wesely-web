function formCheck() {
	var useridField = document.getElementById("userid");
	var userid = useridField.value.trim();

	var passwordField = document.getElementById("password");
	var password = passwordField.value.trim();

	if (!userid || userid.indexOf(" ") > -1) {
		// 입력값이 없으면 스타일 변경 및 애니메이션 적용
		shakeAndHighlight(useridField);
		$("#userid").val("").focus();
		return false;
	}

	if (!password || password.indexOf(" ") > -1) {
		// 입력값이 없으면 스타일 변경 및 애니메이션 적용
		shakeAndHighlight(passwordField);
		$("#password").val("").focus();
		return false;
	}

	return true;
}

function shakeAndHighlight(element) {
	element.classList.add("invalid");

	var animationKeyframes = [
		{ transform: 'translateX(-5px)' },
		{ transform: 'translateX(5px)' },
		{ transform: 'translateX(-5px)' },
		{ transform: 'translateX(0)' }
	];

	var animationOptions = {
		duration: 100,
		direction: 'alternate',
	};

	element.animate(animationKeyframes, animationOptions);

	setTimeout(function() {
		element.classList.remove("invalid");
		element.animate([{ transform: 'none' }], { duration: 0 });
		element.focus();
	}, 300);
}