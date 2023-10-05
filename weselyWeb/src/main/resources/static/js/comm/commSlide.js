// ul
const list = document.querySelector(".slide__list");
// li 태그들
const items = document.querySelectorAll(".slide__item");
// 좌우버튼 들어갈 div
const buttons = document.querySelector(".buttons");
// 하단 페이지 번호 알려주는 div
const paginations = document.querySelector(".paginations");

console.log(items);

const lastIndex = items.length - 1;
let selected = 0;
let interval;

const setTransition = (value) => {
  list.style.transition = value;
};

const setTranslate = ({ index, reset }) => {
  if (reset) list.style.transform = `translate(-520px, 0)`;
  else list.style.transform = `translate(-${(index + 1) * 520}px, 0)`;
};

const activePagination = (index) => {
  [...paginations.children].forEach((pagination) => {
    pagination.classList.remove("on");
  });
  paginations.children[index].classList.add("on");
};

// Clone the first and last elements.
const cloneElement = () => {
  //마지막 li를 복사해서 맨앞에 붙여넣기
  list.prepend(items[lastIndex].cloneNode(true));
  //첫번째 인덱스li를 복사해서 맨뒤에 붙여넣기
  list.append(items[0].cloneNode(true));
  setTranslate({ reset: true });
};

// Make prev and next buttons.
const handlePrev = () => {
  selected -= 1;
  setTransition("transform 0.3s linear");
  setTranslate({ index: selected });
  if (selected < 0) {
    selected = lastIndex;
    setTimeout(() => {
      setTransition("");
      setTranslate({ index: selected });
    }, 300);
  }
  if (selected >= 0) activePagination(selected);
};

const handleNext = () => {
  console.log(selected);
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
  if (selected <= lastIndex) activePagination(selected);
};

const makeButton = () => {
  if (items.length > 1) {
    const prevButton = document.createElement("button");
    prevButton.classList.add("buttons__prev");
    prevButton.innerHTML = '<i class="fas fa-arrow-left"></i>';
    prevButton.addEventListener("click", handlePrev);

    const nextButton = document.createElement("button");
    nextButton.classList.add("buttons__next");
    nextButton.innerHTML = '<i class="fas fa-arrow-right"></i>';
    nextButton.addEventListener("click", handleNext);

    buttons.appendChild(prevButton);
    buttons.appendChild(nextButton);
  }
};

// Make the pagination buttons.
const handlePagination = (e) => {
  // e.target.dataset은 HTML 요소에 data-* 형태로 설정된 사용자 정의 데이터를 참조합니다.
  // 이 경우, 각 페이지네이션 버튼에 'data-num' 속성으로 각각의 인덱스 번호가 저장되어 있습니다.
  if (e.target.dataset) {
    // 'data-num' 속성값(문자열)을 숫자로 변환하여 selected 변수에 저장합니다.
    selected = parseInt(e.target.dataset.num);

    // 슬라이드 이동 효과를 설정합니다.
    setTransition("all 0.3s linear");

    // 슬라이드 이동 위치를 설정합니다.
    setTranslate({ index: selected });

    // 현재 선택된 페이지네이션 버튼을 활성화 상태로 변경합니다.
    activePagination(selected);
  }
};

const makePagination = () => {
  if (items.length > 1) {
    for (let i = 0; i < items.length; i++) {
      const button = document.createElement("button");
      button.dataset.num = i;
      button.classList.add("pagination");
      if (i === 0) {
        button.classList.add("on");
      }
      paginations.appendChild(button);
      paginations.addEventListener("click", handlePagination);
    }
  }
};

const render = () => {
  makeButton();
  makePagination();
  cloneElement();
};

render();
