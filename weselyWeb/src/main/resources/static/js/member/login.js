
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