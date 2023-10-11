// 사업자 정보 입력부분 js

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


/*
// 개업일자 보류
//==========================================================
// '개업연도' 셀렉트 박스 option 목록 동적 생성
//==========================================================
const birthYear = document.querySelector('#birthyear')
// option 목록 생성 여부 확인
isYearOptionExisted = false;
birthYear.addEventListener('focus', function() {
	// year 목록 생성되지 않았을 때 (최초 클릭 시)
	if (!isYearOptionExisted) {
		isYearOptionExisted = true
		for (var i = 1923; i <= 2023; i++) {
			// option element 생성
			const YearOption = document.createElement('option')
			YearOption.setAttribute('value', i)
			YearOption.innerText = i
			// birthYear의 자식 요소로 추가
			this.appendChild(YearOption);
		}
	}
});


//==========================================================
// '개업월' 셀렉트 박스 option 목록 동적 생성
//==========================================================
const birthMonth = document.querySelector("#birthmonth")
// option 목록 생성 여부 확인
isMonthOptionExisted = false;
birthMonth.addEventListener('focus', function() {
	// day 목록 생성되지 않았을 때 (최초 클릭 시)
	if (!isMonthOptionExisted) {
		isMonthOptionExisted = true
		for (var i = 1; i <= 12; i++) {
			//option 생성
			const MonthOption = document.createElement('option')
			MonthOption.setAttribute('value', i)
			MonthOption.innerHTML = i
			//birthDay의 자식요소로 추가
			this.appendChild(MonthOption);
		}
	}
});

//==========================================================
// '개업일자' 셀렉트 박스 option 목록 동적 생성
//==========================================================
const birthDay = document.querySelector("#birthday")
// option 목록 생성 여부 확인
isDayOptionExisted = false;
birthDay.addEventListener('focus', function() {
	// day 목록 생성되지 않았을 때 (최초 클릭 시)
	if (!isDayOptionExisted) {
		isDayOptionExisted = true
		for (var i = 1; i <= 31; i++) {
			//option 생성
			const DayOption = document.createElement('option')
			DayOption.setAttribute('value', i)
			DayOption.innerHTML = i
			//birthDay의 자식요소로 추가
			this.appendChild(DayOption);
		}

	}
});
*/

//==========================================================
// 폼 체크
//==========================================================

function formCheck() {
	// 아이디 폼체크
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
	} else if ($("#bname").val() == null || $("#name").val().trim().length == 0) {
		alert("대표자성명은 반드시 입력해야 합니다.");
		$("#bname").val("");
		$("#bname").focus();
		return false;
	} else if ($("#birth-year").val() == null) {
		alert("개업연도를 선택해주세요.");
		$("#birth-year").focus();
		return false;
	} else if ($("#birth-month").val() == null) {
		alert("개업월(달)을 선택해주세요.");
		$("#birth-month").focus();
		return false;
	} else if ($("#birth-day").val() == null) {
		alert("개업일을 선택해주세요.");
		$("#birth-day").focus();
		return false;
	} else if (window.confirm("사업자 정보입력을 완료하시겠습니까?")) {
		location.href = "/member/businessJoinOk";
	} else {
		return false;
	};
}










