const btnAdd = document.querySelector('.btnAddReview'); // 리뷰작성하기 버튼 (모달생성)
const btnCanclePost = document.querySelector('.btnCanclePost'); // 리뷰작성 취소(모달가리기)
const modal = document.querySelector('.reviewModal'); // 리뷰 모달
const overlay = document.querySelector('.overlay'); // 오버레이
const stars = document.querySelectorAll('.stars i'); // 별점 아이콘
const starRatingInput = document.getElementById('starRating'); // 별점 value담는 input
const reviewContent = document.querySelector('.reviewTextBox'); // 콘텐츠 textarea 

//=============================================================================================================

// 리뷰작성하기 버튼 클릭시
btnAdd.addEventListener('click', showModal);
// 취소 버튼 클릭시
btnCanclePost.addEventListener('click', hideModal);
// 별점 아이콘 클릭시
stars.forEach((star, select) => {
	star.addEventListener('click', () => {
		handleStarRating(select)
	});
});

// 신규 리뷰작성 모달창 표시
function showModal() {
	modal.classList.add('on');
	overlay.classList.add('on');
}

// 모달창 숨기기
function hideModal() {
	modal.classList.remove('on');
	overlay.classList.remove('on');

	// 별점 초기화
	stars.forEach(star => star.classList.remove("active"));
	starRatingInput.value = '';
	reviewContent.value = '';
}

// 별점 처리
function handleStarRating(select) {
	stars.forEach((star, total) => {
		select >= total ? star.classList.add("active") : star.classList.remove("active");
		// 선택된 별의 개수를 hidden input의 value로 설정한다.
		starRatingInput.value = select + 1;
	});
}


// 리뷰등록 버튼 이벤트핸들러 : 새로운 리뷰등록
function postReview() {
	let nickname = document.getElementById('nickname').value; // 유저닉네임
	let userProfile = document.getElementById('userProfile').value; // 유저프로필(사진)
	let kakaoId = document.getElementById('kakaoId').value;
	let region = document.getElementById('region').value;
	let address = document.getElementById('address').value;
	let name = document.getElementById('name').value;
	let hashTag = document.getElementById('hashTag').value;
	let tel = document.getElementById('tel').value;
	let refId;

	// 별점의 value 없을 때 (클릭없을 시)
	if (starRating.value == '') {
		alert('별점은 필수사항입니다. 별점을 클릭해주세요.');
		return false;
	}

	// text 없을 떄 
	let v = reviewContent.value;
	if (v == null || v.trim().length == 0) {
		alert('내용은 필수사항입니다.');
		reviewContent.value = '';
		reviewContent.focus();
		return false;
	}

	const store = {
		userid: "",
		name: name,
		address: address,
		opening: "",
		tel: tel,
		description: "",
		hashTag: hashTag,
		kakaoId: kakaoId,
		region: region
	};

	// 스토어 정보 저장 요청 설정
	let storeUrl = '/api/addStores';
	let storeOptions = {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify([store])
	};

	fetch(storeUrl, storeOptions)
		.then(response => response.json())
		.then(data => {
			refId = data;
			//multipart/form-data 형식으로 데이터를 보내는 것은 가능합니다. 이 형식은 주로 파일 업로드와 같이 바이너리 데이터를 전송할 때 사용되며, 폼 필드와 그 값을 함께 보낼 수 있습니다.
			//JavaScript에서 FormData 객체를 사용하면 간단하게 multipart/form-data 형식으로 데이터를 만들어 낼 수 있습니다. 
			let reviewForm = new FormData();
			reviewForm.append('ref', data);
			reviewForm.append('nickname', nickname);
			reviewForm.append('userProfile', userProfile);
			reviewForm.append('star', starRatingInput.value);
			reviewForm.append('content', v);

			// 리뷰 저장 요청 설정
			fetch('/api/reviewInsert', {
				method: 'POST',
				body: reviewForm
			})
				.then(response => response.json()) // 여기 추가
				.then(data => {
					console.log(data);
					location.href = "/store/view/" + refId;
				})
				.catch(e => alert('에러발생!! : ', e.message));
		})
		.catch(e => alert('에러발생!! : ', e.message));

	return false;
}