// 미세먼지
if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(
		function(position) {
			let lat = position.coords.latitude;
			let lon = position.coords.longitude;

			fetch(
				`http://api.openweathermap.org/data/2.5/air_pollution?lat=${lat}&lon=${lon}&appid=aa1d55a2e7e667baebd92b3946d66932&units=metric`
			)
				.then((response) => response.json())
				.then((mi) => {
					let pm10 = mi.list[0].components.pm10;

					let great = "../images/great.png";
					let mise = "../images/mise.png";
					let normal = "../images/normal.png";
					let bad = "../images/bad.png";
					let bbad = "../images/bbad.png";

					let imgElement = document.getElementById("weatherImage");

					if (pm10 > 0 && pm10 <= 20) {
						imgElement.src = great;
						document.querySelector(".dust").textContent += "매우좋음";
					} else if (pm10 > 20 && pm10 <= 50) {
						imgElement.src = mise;
						document.querySelector(".dust").textContent += "좋음";
					} else if (pm10 > 50 && pm10 <= 100) {
						imgElement.src = normal;
						document.querySelector(".dust").textContent += "보통";
					} else if (pm10 > 100 && pm10 <= 200) {
						imgElement.src = bad;
						document.querySelector(".dust").textContent += "나쁨";
					} else if (pm10 >= 200) {
						imgElement.src = bbad;
						document.querySelector(".dust").textContent += "매우나쁨";
					}
				})

				.catch((error) => {
					console.log("Error:", error);
				});
		},
		function(error) {
			console.error(error);
		}
	);
} else {
	console.error("Geolocation is not supported by this browser.");
}

// 날씨
if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(function(position) {
		let lat = position.coords.latitude;
		let lon = position.coords.longitude;

		fetch(
			`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=aa1d55a2e7e667baebd92b3946d66932&units=metric`
		)
			.then((response) => response.json())
			.then((result) => {
				let temp = Math.round(result.main.temp);
				let weather = result.weather[0].main;
				let weather1 = result.weather[0].description;

				document.querySelector(".temp").textContent = temp;

				currentTemp = temp;
				// 비오는날
				if (
					weather === "Rain" ||
					weather === "Drizzle" ||
					weather === "Thunderstorm"
				) {
					for (let i = 1; i <= 4; i++) {
						var rainDiv = document.createElement("div");
						rainDiv.className = `rain${i}`;
						document.querySelector(".window").appendChild(rainDiv);
					}

					var introText = document.querySelector(".introText");
					introText.innerHTML = "비가 내리는 날씨! <br />실내에서 운동할까요?";
					//document.querySelector(".sm1").appendChild(smText);

					let keyframes = [
						{ transform: "rotate(10deg) translateY(0px)" },
						{ transform: "rotate(10deg) translateY(100px)" },
					];

					Array.from(document.querySelectorAll(".window div")).forEach(
						(element, index) => {
							let options = {
								duration: 1200 + index * 100,
								iterations: Infinity,
							};

							element.animate(keyframes, options);
						}
					);

					// 선선한날
				} else if (
					weather1 === "few clouds" ||
					weather1 === "scattered clouds"
				) {
					document.querySelector(".inAct img").src = "/images/runAct.png";

					var runImgElement = document.createElement("img");
					runImgElement.className = "run";
					runImgElement.src = "/images/run.png";
					document.querySelector(".inAct").appendChild(runImgElement);

					document.querySelector(".window").style.display = "none";

					var introText = document.querySelector(".introText");
					introText.innerHTML = "선선한 날씨! <br />야외활동은 어떠세요?";
					//document.querySelector(".introText").appendChild(introText);

					let keyframes = [
						{ transform: "translateY(-5%)", rotate: "-2deg" },
						{ transform: "translate(5%,0%)", rotate: "2deg" },
						{ transform: "translateY(2%)", rotate: "-2deg" },
					];

					let keyframes1 = [
						{ transform: "translateX(0%)" },
						{ transform: "translateX(-100%)" },
					];

					let options = {
						duration: 2000,
						iterations: Infinity,
					};

					let options1 = {
						duration: 2000,
						iterations: Infinity,
					};

					Array.from(document.querySelectorAll(".inAct img")).forEach(
						(element, index) => {
							if (index === 0) element.animate(keyframes, options);
							else element.animate(keyframes1, options1);

							// Assuming '.window img' exists.
							if (document.querySelector(".window img"))
								document
									.querySelector(".window img")
									.animate(keyframes1, options1);
						}
					);

					// 흐린날
				} else if (
					weather1 === "broken clouds" ||
					weather1 === "overcast clouds"
				) {
					document.querySelector(".inAct img").src = "/images/calm.png";

					var backGroundImgElement = document.createElement("img");
					backGroundImgElement.className = "backGround";
					backGroundImgElement.src = "/images/backGround.png";
					document.querySelector(".inAct").appendChild(backGroundImgElement);

					var cloudImgElement = document.createElement("img");
					cloudImgElement.className = "cloud";
					cloudImgElement.src = "/images/cloud.png";
					document.querySelector(".window").appendChild(cloudImgElement);

					var introText = document.querySelector(".introText");
					introText.innerHTML = "흐린 날씨! <br />차분한 실내운동은 어떠세요?";
					//document.querySelector(".introText").appendChild(introText);

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
					document.querySelector(".inAct img").src = "/images/teni.png";

					var ballImgElement = document.createElement("img");
					ballImgElement.className = "ball";
					ballImgElement.src = "/images/ball.png";
					document.querySelector(".inAct").appendChild(ballImgElement);

					document.querySelector(".window").style.display = "none";

					var introText = document.querySelector(".introText");
					introText.innerHTML = "화창한 날씨! <br />야외활동은 어떠세요?";
					//document.querySelector(".introText").appendChild(introText);

					let keyFrames = [
						{ top: "-80px", left: "-100px" },
						{ top: "0px", left: "50px" },
					];

					let keyFrames1 = [{ left: "40px" }, { left: "0", rotate: "-2deg" }];

					let options = {
						duration: 1100,
						iterations: Infinity,
					};

					document.querySelector(".ball").animate(keyFrames, options);
					document.querySelector(".inAct img").animate(keyFrames1, options);
					// 나머지 비오는날 = (Snow Mist Smoke Haze Dust Fog Sand Dust Ash Squall Tornado)
				} else {
					document.querySelector(".inAct img").src = "/images/calm.png";

					var backGroundImgElement = document.createElement("img");
					backGroundImgElement.src = "/images/backGround.png";
					document
						.querySelector(".inAct img")
						.appendChild(backGroundImgElement);

					var cloudImgElement = document.createElement("img");
					cloudImgElement.className = "cloud";
					cloudImgElement.src = "/images/cloud.png";
					document.querySelector(".window").appendChild(cloudImgElement);

					var introText = document.querySelector(".introText");
					introText.innerHTML = "흐린 날씨! <br />차분한 실내운동은 어떠세요?";
					//document.querySelector(".introText").appendChild(introText);

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

				if (navigator.geolocation) {
					navigator.geolocation.getCurrentPosition(function(position) {
						let lat = position.coords.latitude;
						let lon = position.coords.longitude;

						let now = new Date();
						now.setDate(now.getDate() - 1); // 하루 전으로 시간을 설정

						let yesterdayStr = formatDate(now);

						let apiKey = "2399NSNUHN9TFXYSWNKYFCAWG"; // 발급받은 API 키 입력

						// Visual Crossing API 호출 부분
						fetch(
							`https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/${lat},${lon}/${yesterdayStr}?key=${apiKey}&include=obs`
						)
							.then((response) => response.json())
							.then((data) => {
								let yesterdayTemp = Math.round(((data.days[0].temp - 32) * 5) / 9); // 화씨에서 섭씨로 변환
								let diff = currentTemp - yesterdayTemp; // 현재온도와 어제온도 차이 계산

								if (currentTemp > yesterdayTemp) {
									document.querySelector(".compareTempText").innerHTML =
										"어제 평균온도보다" + diff + "°C 높아요";
								} else if (currentTemp < yesterdayTemp) {
									document.querySelector(".compareTempText").innerHTML =
										"어제 평균온도보다 " + Math.abs(diff) + "°C 낮아요";
								}
								// 모든 작업이 완료된 후 로딩화면 숨기기
								document.getElementById('overlay').style.display = 'none';
								document.getElementById('loading').style.display = 'none';

							})
							.catch((error) => console.log("Error:", error));
					});
				}
			});
	});
}
