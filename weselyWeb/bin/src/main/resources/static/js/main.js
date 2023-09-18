function getCurrentLocation() {
  // 현재 위치 가져오기
  navigator.geolocation.getCurrentPosition((position) => {
    let lat = position.coords.latitude;
    let lon = position.coords.longitude;
    console.log("현재 위치", lat, lon);
  });
}

// 미세먼지
$.getJSON(
  "http://api.openweathermap.org/data/2.5/air_pollution?lat=35.8695&lon=128.6061&appid=aa1d55a2e7e667baebd92b3946d66932&units=metric",
  function (mi) {
    let pm10 = mi.list[0].components.pm10;

    let great = "../images/great.png";
    let mise = "../images/mise.png";
    let normal = "../images/normal.png";
    let bad = "../images/bad.png";
    let bbad = "../images/bbad.png";
    let pm10Grade = "pm10";

    let imgElement = document.getElementById("weatherImage");
    if (pm10 > 0 && pm10 <= 20) {
      imgElement.src = great;
      $(".dust").append("매우좋음");
    } else if (pm10 > 20 && pm10 <= 50) {
      imgElement.src = mise;
      $(".dust").append("좋음");
    } else if (pm10 > 50 && pm10 <= 100) {
      imgElement.src = normal;
      $(".dust").append("보통");
    } else if (pm10 > 100 && pm10 <= 200) {
      imgElement.src = bad;
      $(".dust").append("나쁨");
    } else if (pm10 >= 200) {
      imgElement.src = bbad;
      $(".dust").append("매우나쁨");
    }
  }
);

// 날씨
$.getJSON(
  "https://api.openweathermap.org/data/2.5/weather?lat=35.8695&lon=128.6061&appid=aa1d55a2e7e667baebd92b3946d66932&units=metric",
  function (result) {
    let temp = Math.round(result.main.temp);
    let weather = result.weather[0].main;
    let weather1 = result.weather[0].description;
    $(".temp").text(temp);

    currentTemp = temp;

    let loc = result.name;

    console.log(loc);
    console.log(result);
    // 비오는날
    //Drizzle = 이슬비 , Thunderstorm = 뇌우
    if (
      weather === "Rain" ||
      weather === "Drizzle" ||
      weather === "Thunderstorm"
    ) {
      $(".window").append('<div class="rain1"></div>');
      $(".window").append('<div class="rain2"></div>');
      $(".window").append('<div class="rain3"></div>');
      $(".window").append('<div class="rain4"></div>');
      $(".introText").append("비가 내리는 날씨! <br />실내에서 운동할까요?");

      let keyframes = [
        { transform: "rotate(10deg) translateY(0px)" },
        { transform: "rotate(10deg) translateY(100px)" },
      ];

      let rainElements = document.querySelectorAll(".window div");

      rainElements.forEach((element, index) => {
        let options = {
          duration: 1200 + index * 100,
          iterations: Infinity,
        };
        element.animate(keyframes, options);
      });
      // 선선한날
    } else if (weather1 === "few clouds" || weather1 === "scattered clouds") {
      $(".inAct img").attr("src", "/images/runAct.png");

      $(".inAct").append('<img class = "run" src="/images/run.png">');
      $(".window").attr("style", "display: none;");
      $(".introText").append("선선한 날씨! <br />야외활동은 어떠세요?");
      let keyframes = [
        { transform: "translateY(0%)" },
        { transform: "translateY(10%)" },
        { transform: "translateY(5%)" },
      ];
      let keyframes1 = [
        { transform: "translateX(0%)" },
        { transform: "translateX(-100%)" },
      ];
      let options = {
        duration: 800,
        iterations: Infinity,
      };
      let options1 = {
        duration: 3000,
        iterations: Infinity,
      };
      document.querySelector(".inAct img").animate(keyframes, options);
      document
        .querySelector(".inAct img:last-child")
        .animate(keyframes1, options1);
      document.querySelector(".window img").animate(keyframes1, options1);
      // 흐린날
    } else if (weather1 === "broken clouds" || weather1 === "overcast clouds") {
      $(".inAct img").attr("src", "/images/calm.png");
      $(".inAct").append(
        '<img class="backGround" src="/images/backGround.png">'
      );
      $(".window").append('<img class="cloud" src="/images/cloud.png">');
      $(".introText").append("흐린 날씨! <br />차분한 실내운동은 어떠세요?");

      let keyFrames = [
        { transform: "translateX(0%)" },
        { transform: "translateX(-100%)" },
      ];
      let options = {
        duration: 9000,
        iterations: Infinity,
      };
      document.querySelector(".window img").animate(keyFrames, options);

      //화창한날
    } else if (weather === "Clear") {
      $(".inAct img").attr("src", "/images/teni.png");
      $(".inAct").append('<img class="ball" src="/images/ball.png">');
      $(".window").attr("style", "display: none;");
      $(".introText").append("화창한 날씨! <br />야외활동은 어떠세요?");

      let keyFrames = [
        { transform: "translateX(0%)" },
        { transform: "translateX(-100%)" },
      ];
      let options = {
        duration: 1000,
        iterations: Infinity,
      };
      document.querySelector(".ball").animate(keyFrames, options);
      // 나머지 비오는날 = (Snow Mist Smoke Haze Dust Fog Sand Dust Ash Squall Tornado)
    } else {
      $(".inAct img").attr("src", "/images/calm.png");
      $(".inAct img").append("src", "/images/backGround.png");
      $(".window").append('<img class="cloud" src="/images/cloud.png">');
      $(".introText").append("흐린 날씨! <br />차분한 실내운동은 어떠세요?");

      let keyFrames = [
        { transform: "translateX(0%)" },
        { transform: "translateX(-100%)" },
      ];
      let options = {
        duration: 9000,
        iterations: Infinity,
      };
      document.querySelector(".window img").animate(keyFrames, options);
    }

    function formatDate(date) {
      let d = new Date(date),
        month = "" + (d.getMonth() + 1),
        day = "" + d.getDate(),
        year = d.getFullYear();

      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;

      return [year, month, day].join("-");
    }

    let now = new Date();
    now.setDate(now.getDate() - 1); // 하루 전으로 시간을 설정

    let yesterdayStr = formatDate(now);

    let locationName = "Daejeon"; // 원하는 위치명 입력
    let apiKey = "2399NSNUHN9TFXYSWNKYFCAWG"; // 발급받은 API 키 입력

    // 어제의 온도 가져오기
    fetch(
      `https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/${locationName}/${yesterdayStr}?key=${apiKey}&include=obs`
    )
      .then((response) => response.json())
      .then((data) => {
        yesterdayTemp = Math.round(((data.days[0].temp - 32) * 5) / 9); // 화씨에서 섭씨로 변환
        let diff = currentTemp - yesterdayTemp; // 현재온도와 어제온도 차이 계산

        if (currentTemp > yesterdayTemp) {
          document.querySelector(".compareTempText").innerHTML =
            "어제 평균온도보다" + diff + "°C 높아요";
        } else if (currentTemp < yesterdayTemp) {
          document.querySelector(".compareTempText").innerHTML =
            "어제 평균온도보다 " + Math.abs(diff) + "°C 낮아요";
        }
      })
      .catch((error) => console.error("Error:", error));
  }
);

// 슬라이드 기능
// let slides = document.querySelector('.slide ');
// let slideDiv = document.querySelectorAll('.slide div');
// currentIdx = 0;
// slideCount = slideDiv.length;
// LBtn = document.querySelector('.LBtn');
// RBtn = document.querySelector('.RBtn');
// slideWidth = 300;
// slideMargin = 10;
// copy(); // 처음 이미지 마지막이미지 복사 함수
// reset(); // 슬라이드 넓이 위치값 초기화 함수

// function copy() {
//   let cloneSlideFirst = slideDiv[0].cloneNode(true);
//   let cloneSlideLast = slides.lastElementChild.cloneNode(true);
//   slides.append(cloneSlideFirst);
//   slides.insertBefore(cloneSlideLast, slides.firstElementChild);
// }
// function reset() {
//   slides.style.width = (slideWidth + slideMargin) * (slideCount + 1) + 'px';
//   slides.style.left = -(slideWidth + slideMargin) + 'px';
// }

// RBtn.addEventListener('click', function () {
//   //오른쪽 버튼 눌렀을때
//   if (currentIdx <= slideCount - 1) {
//     //슬라이드이동
//     slides.style.left = -(currentIdx + 2) * (slideWidth + slideMargin) + 'px';
//     slides.style.transition = `${0.5}s ease-out`; //이동 속도
//   }
//   if (currentIdx === slideCount - 1) {
//     //마지막 슬라이드 일때
//     setTimeout(function () {
//       //0.5초동안 복사한 첫번째 이미지에서, 진짜 첫번째 위치로 이동
//       slides.style.left = -(slideWidth + slideMargin) + 'px';
//       slides.style.transition = `${0}s ease-out`;
//     }, 500);
//     currentIdx = -1;
//   }
//   currentIdx += 1;
// });
// LBtn.addEventListener('click', function () {
//   //이전 버튼 눌렀을때
//   console.log(currentIdx);
//   if (currentIdx >= 0) {
//     slides.style.left = -currentIdx * (slideWidth + slideMargin) + 'px';
//     slides.style.transition = `${0.5}s ease-out`;
//   }
//   if (currentIdx === 0) {
//     setTimeout(function () {
//       slides.style.left = -slideCount * (slideWidth + slideMargin) + 'px';
//       slides.style.transition = `${0}s ease-out`;
//     }, 500);
//     currentIdx = slideCount;
//   }
//   currentIdx -= 1;
// });
