let latitude;
let longitude;

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 현재 위치의 위도, 경도 알아내기
navigator.geolocation.getCurrentPosition((position) => {
	latitude = position.coords.latitude;
	longitude = position.coords.longitude;

	// 알아낸 위도,경도를 kakaoAPI로 파라미터로 넘겨 법정동으로 받아내기
	geocoder.coord2RegionCode(longitude, latitude, callback);
});

var geocoder = new kakao.maps.services.Geocoder();


var callback = function(result, status) {
	if (status === kakao.maps.services.Status.OK) {
		loc = result[0].region_2depth_name + result[0].region_3depth_name;

		// 현재 위치 표시 00시 00구 00동
		document.getElementById('currentLocation').innerText =
			result[0].region_1depth_name + " " +
			result[0].region_2depth_name + " " +
			result[0].region_3depth_name;
	}
};