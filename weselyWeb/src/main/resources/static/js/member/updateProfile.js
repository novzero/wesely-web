// ========================================================================================================
// 프로필 사진
// ========================================================================================================

$(function() {
	let photo_path = $('.profile-photo').attr('src');
	let my_photo; // 사용자가 업로드할 이미지를 담는다.
	$("#upload").change(function() {
		my_photo = this.files[0];
		if (!my_photo) {
			$(".profile-photo").attr("src", photo_path);
			return;
		}
		if (my_photo.size > 1024 * 1024) {
			alert(Math.round(my_photo.size / 1024 / 1024) + 'MB(1MB까지만 업로드 가능)');
			$(".profile-photo").attr("src", photo_path);
			$(this).val('');
			return;
		}

		// 이미지 미리보기
		let reader = new FileReader();

		reader.onload = function() {
			$(".modal-photo").attr("src", reader.result);
		};

		reader.readAsDataURL(my_photo);
	});
	
	// 등록버튼 눌렀을 때
	$("#photo_submit").click(function() {
		// 업로드한 이미지 여부 확인
		if ($("#upload").val() == '') {
			alert("프로필 사진을 선택해주세요.");
			$(".profile-photo").attr("src", photo_path);
			return;
		}

		//파일 전송
		let form_data = new FormData();
		form_data.append("image", my_photo); // 사용자가 업로드한 이미지

		// Ajax 요청 보내기
		$.ajax("uploadImage", {
			type: "POST",
			data: form_data,
			processData: false,
			contentType: false,
			success: function(response) {
				// 성공적으로 요청 처리됨
				console.log(response);

				alert("이미지 업로드 성공");
				closeModal();
				location.reload();
			},
			error: function(error) {
				// 요청 실패 상태 처리하기
				console.error(error);
				alert("이미지 업로드 실패");
			}
		});
	})
})




// ========================================================================================================
// 모달창

const previewButton = document.getElementById('preview-btn');
const modal = document.getElementById('modal');
const closeButton = modal.querySelector('.close-button');
const upload = document.getElementById('upload');
const imagePreview = document.getElementById('image-preview');
const submitButton = modal.querySelector('.submit-button');

// 프로필 사진 클릭 시 모달 열기
previewButton.addEventListener('click', () => {
	modal.style.display = 'block';
});

// 모달 닫기 버튼 클릭 시 모달 닫기
closeButton.addEventListener('click', () => {
	modal.style.display = 'none';
});

function modalOn() {
	modal.style.display = "flex"
}
function isModalOn() {
	return modal.style.display === "flex"
}
function modalOff() {
	modal.style.display = "none"
}
function closeModal() {
	modalOff();
}


closeButton.addEventListener("click", function() {
	closeModal();
})
modal.addEventListener("click", function(e) {
	const evTarget = e.target
	if (evTarget.classList.contains("modal-overlay")) {
		closeModal();
	}
})
window.addEventListener("keyup", function(e) {
	if (isModalOn() && e.key === "Escape") {
		closeModal();
	}
})



// ========================================================================================================


// ========================================================================================================
// 유효성 검사
// ========================================================================================================

$(function() {

	// 닉네임 검사 | 2글자 이상 5글자 이하로 작성
	$("#nickname").keyup(function() {
		var value = $(this).val();
		var reg = /^[A-Za-z가-힣]{2,5}$/;

		if (value != null && value.length >= 1) {
			if (!reg.test(value)) {
				$("#nickmessage").css('color', 'red').html("공백제외 2~5자로 한글 또는 영문만 가능합니다.");
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
						$("#nickmessage").html("이미 존재하는 닉네임입니다.").css('color', 'red');
					} else {
						$("#nickmessage").html("사용가능한 닉네임입니다.").css('color', 'green');
					}
				},
				error: function() {
					alert("에러!!!");
				}
			});
		} else {
			$("#nickmessage").html(""); // 1자 미만이면 메세지 삭제
		}
	});
});

// ========================================================================================================
// 폼체크
// ========================================================================================================
function formCheck() {
	var nicknameField = document.getElementById("nickname");
	var nickname = nicknameField.value.trim();

	if (!nickname || nickname.indexOf(" ") > -1) {
		shakeAndHighlight(nicknameField);
		$("#nickname").val("").focus();
		return false;
	} else if ($("#nickmessage").css('color') == 'rgb(255, 0, 0)') {
		shakeAndHighlight(nicknameField);
		$("#nickname").val("").focus();
		return false;
	} else if (window.confirm("회원정보를 수정하시겠습니까?")) {
		location.reload();
	} else {
		return false;
	};
};

function deleteMember() {
	if (window.confirm("wesely를 탈퇴하시겠습니까?")) {
		location.href = "/member/delete";
	} else {
		location.href = "redirect:/member/updateProfile";
	}
}
// ========================================================================================================
// shake&하이라이팅 효과
// ========================================================================================================

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

