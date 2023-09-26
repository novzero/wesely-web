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
	let ref = document.getElementById('ref').value; // 시설의 고유 key

	// 별점의 value 없을 때 (클릭없을 시)
	if (starRating.value == '') {
		alert('별점은 필수사항입니다. 별점을 클릭해주세요.');
		// text 없을 떄 
	} else if (reviewContent.value == '') {
		alert('내용은 필수사항입니다.');
	} // 그 외
	else {
		reviewForm.action = '/api/reviewInsert';
		reviewForm.method = 'post'; // POST 방식으로 변경

		// URL과 옵션 설정
		let url = '/api/reviewInsert';
		let options = {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			// 필드명에 맞게 key-value 쌍 생성
			body: JSON.stringify({
				ref: ref,
				nickname: nickname,
				userProfile: userProfile,
				star: starRatingInput,
				content: reviewContent
			})
		};

		// AJAX 요청 보내기
		fetch(url, options)
			.then(response => {
				console.log(response.data);
				location.reload();
			})
			.catch(error => alert('에러발생!! : ', error));
	}
};


// 리뷰수정버튼을 클릭했을 때 실행할 함수 (한개 읽어오기)
function updateBtn(id) {
	let getId = document.querySelector('#reviewID').value; // 리뷰의 고유 id
	let submitBtn = document.getElementById('submitBtn'); //'등록' button 태그
	let modifyBtn = document.getElementById('modifyBtn'); // '수정' input:button
	let reviewForm = document.getElementById('reviewForm'); // 리뷰 폼 form

	console.log(getId); // GET 요청전 임의로 먹여둔 리뷰의 id 값 : emptyID (테스트용)

	// 모달 보이게
	showModal();

	axios.get('/api/reviewByID/' + id) // GET 요청 보내기
		.then(response => {
			const data = response.data;
			console.log(data)

			// GET요청에 의한 데이터가 넘어왔다면
			if (data) {
				// 가져온 데이터로 모달 내부의 필드들을 채워 넣습니다.
				reviewContent.value = data.content; //text
				starRatingInput.value = data.star; //star

				getId = data.id; // 리뷰의 고유 id (테스트용)
				// ✅ tag자체의 value에 직접 떠먹여줘야 함.
				document.querySelector("#reviewID").value = data.id;
				reviewForm.action = '/api/reviewUpdate/' + data.id;
				reviewForm.method = 'put'; // put 방식으로 변경

				// '등록' 버튼 숨기기
				submitBtn.style.display = 'none';

				// '수정' 버튼 나타나게
				modifyBtn.style.display = 'block';

				// 가져온 별점 값으로 별 아이콘 클래스 설정하기 
				for (let i = 0; i < data.star; i++) {
					stars[i].classList.add('active');
				}

			} else { // 데이터가 넘어오지 않은 경우
				alert('오류입니다. 다시 시도해주세요.');
			}
		})
		.catch((error) => {
			console.log('Error:', error);
			alert('오류가 발생하였습니다.');
		});
};

// 수정 폼 안에서 '수정'버튼 클릭시 이벤트핸들러 : 서버에 PUT 요청
function sendPutRequest() {
	let id = document.querySelector("#reviewID").value; // 댓글의 고유 id
	let prevStar = starRatingInput.value; // 기존의 star 값 받기
	let prevText = reviewContent.value; // 기존의 text 값 받기

	console.log(prevStar, prevText);

	// FormData 객체를 만든다.
	let form = new FormData();
	// formData.append을 사용하여 key/value 쌍을 추가
	form.append("id", id);
	form.append("star", prevStar);
	form.append("content", prevText);
	// PUT 요청
	axios.put('/api/reviewUpdate', form)
		.then(response => {
			console.log(response.data);
			location.reload(); // 페이지 새로고침
			hideModal(); // 모달 숨기기
		})
		.catch(error => alert(`에러발생!! : ${error}`));
};

// 리뷰삭제 버튼클릭 시 이벤트핸들러 : 서버에 DELETE 요청
function sendDeleteRequest(id) {
	axios.delete('/api/reviewDelete/' + id)
		.then(response => {
			if (response.data) {
				alert('리뷰가 성공적으로 삭제되었습니다.');
				location.reload(); // 페이지 새로고침
			} else {
				alert('리뷰 삭제에 실패하였습니다.');
			}
		})
		.catch((error) => {
			console.error('Error:', error);
			alert('리뷰 삭제 중 오류가 발생하였습니다.');
		});
};

