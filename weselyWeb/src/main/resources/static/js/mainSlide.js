// ul
const list = document.querySelector(".slide__list");
// li 태그들
let items = document.querySelectorAll(".slide__item");
// 좌우버튼 들어갈 div
const buttons = document.querySelector(".buttonBox");
// 하단 페이지 번호 알려주는 div
const paginations = document.querySelector(".paginations");

console.log(items);

const lastIndex = items.length - 1;
let selected = 0;
let interval;

// Util Functions : 전역에서 사용되는 특정 로직 & 독립적인 기능
// 트랜지션 효과주기
const setTransition = (value) => {
	list.style.transition = value;
};
// 이동 효과주기
const setTranslate = ({ index, reset }) => {
	// 렌더링 시 (처음 화면)
	if (reset) {
		list.style.transform = `translate(-426px, 0)`;
		activeDescription(0);
	}
	// 그 외 경우 (클릭 시  이동)
	else {
		list.style.transform = `translate(-${(index + 1) * 426}px, 0)`;

		activeDescription(index + 1);
	}
};

// 첫번째와 마지막 요소 클론하기
const cloneElement = () => {
	//마지막 li를 복사해서 맨앞에 붙여넣기
	list.prepend(items[lastIndex].cloneNode(true));
	// 첫번째 인덱스li를 복사해서 맨뒤에 붙여넣기
	// 왜 ? ==> 처음 화면에 보이는 아이템 갯수 만큼 복사해야 빈공간 보이지 않고 어색하지 않게 돌아감
	list.append(items[0].cloneNode(true));
	list.append(items[1].cloneNode(true));
	list.append(items[2].cloneNode(true));
	// 렌더링 했을 때 첫화면에서 복사된 마지막 요소 가릴 수 있게 -426px만큼 x축 이동시키기
	setTranslate({ reset: true });

	// items 업데이트: 모든 .slide__item (원본 + 클론) 포함
	items = document.querySelectorAll(".slide__item");
};

// 좌측 버튼 클릭 시
const handlePrev = () => {
	// 자동슬라이드 중단
	clearInterval(interval);
	// 클릭할 때 마다 select 기본값 0으로 시작한 값에서 -1씩 한 값이 저장됨
	selected -= 1;
	// console.log(selected); // 확인해요 !
	setTransition("transform 0.3s linear");
	setTranslate({ index: selected });
	// 선택값이 0보다 작은경우(클론된 마지막요소가 보일 때<가짜>)
	if (selected < 0) {
		selected = lastIndex; // 마지막 인덱스로 변경 (원본 마지막요소<진짜>)
		setTimeout(() => {
			// 트랜지션 효과없애서 자연스럽게 만들기
			setTransition("");
			// 위치 이동으로 자연스럽게 만들기
			setTranslate({ index: selected });
		}, 300);
	}
};
// 우측 버튼 클릭 시
const handleNext = () => {
	// 자동슬라이드 중단
	clearInterval(interval);
	selected += 1;
	setTransition("transform 0.3s linear");
	setTranslate({ index: selected });
	if (selected > lastIndex) {
		selected = 0;
		setTimeout(() => {
			setTransition("");
			setTranslate({ index: selected });
		}, 300);
	}
};

const activeDescription = (index) => {
	items.forEach((li) => {
		li.classList.remove("on");
	});

	items[index].classList.add("on");
};

// 자동슬라이드
const autoplayIterator = () => {
	selected += 1;
	setTransition("all 0.3s linear");
	setTranslate({ index: selected });
	if (selected > lastIndex) {
		clearInterval(interval);
		setTimeout(() => {
			selected = 0;
			setTransition("");
			setTranslate({ reset: true });
			autoplay({ duration: 2000 });
		}, 300);
	}
};

const autoplay = ({ duration }) => {
	interval = setInterval(autoplayIterator, duration);
};

const render = () => {
	cloneElement();
	autoplay({ duration: 2000 });
};

render();
