# 서버-클라이언트 통신
## Web API
Web Application Programming Interface의 줄임말로, 서버에 요청을 보내고 응답을 받기 위해 정의된 명세를 뜻합니다.

## JSON
JavaScript Object Notation의 줄임말로, 브라우저-서버 간 통신에서 데이터를 전달하기 위해 인간이 읽을 수 있는 텍스트를 사용하는 개방형 표준 포맷입니다.

## GET 요청
- GET → 통상적으로 데이터 조회(Read)를 요청할 때
  - 예) 영화 목록 조회

- POST → 통상적으로 데이터 생성(Create), 변경(Update), 삭제(Delete) 요청할 때
  - 예) 회원가입, 회원 탈퇴, 비밀번호 수정

## GET 방식으로 데이터를 전달하는 방법
예시) google.com/search?q=아이폰&sourceid=chrome&ie=UTF-8

- ? : 여기서부터 전달할 데이터가 작성된다는 의미입니다.
- & : 전달할 데이터가 더 있다는 뜻입니다.

위 주소는 google.com의 search 창구에 다음 정보를 전달합니다!
- q=아이폰 (검색어)
- sourceid=chrome (브라우저 정보)
- ie=UTF-8 (인코딩 정보)

# Ajax
AJAX는 "Asynchronous JavaScript and XML"의 줄임말로, 웹 페이지를 새로고침하지 않고도 서버와 데이터를 주고받을 수 있게 해주는 기술입니다. 이를 통해 사용자는 더 빠르고 부드럽게 웹 애플리케이션을 사용할 수 있습니다.

## Ajax 시작하기
### Ajax 기본 골격
```
$.ajax({
  type: "GET", // GET 방식으로 요청한다.
  url: "여기에URL을입력",
  data: {}, // 요청하면서 함께 줄 데이터 (GET 요청 시엔 비워두세요)
  success: function(response){ // 서버에서 준 결과를 response라는 변수에 담음
    console.log(response)  // 서버에서 준 결과를 이용해서 나머지 코드를 작성
  }
})
```

## Ajax 통신의 결괏값 사용하기
###  미세먼지 OpenAPI
```
http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99
```
```
{
  "RealtimeCityAir": {
    "list_total_count": 25,
    "RESULT": {
      "CODE": "INFO-000",
      "MESSAGE": "정상 처리되었습니다"
    },
    "row": [
      {
        "MSRDT": "202502041100",
        "MSRRGN_NM": "도심권",
        "MSRSTE_NM": "중구",
        "PM10": 24,
        "PM25": 16,
        "O3": 0.035,
        "NO2": 0.008,
        "CO": 0.3,
        "SO2": 0.004,
        "IDEX_NM": "보통",
        "IDEX_MVL": 54,
        "ARPLT_MAIN": "O3"
      },
      {
        "MSRDT": "202502041100",
        "MSRRGN_NM": "도심권",
        "MSRSTE_NM": "종로구",
        "PM10": 26,
        "PM25": 16,
        "O3": 0.033,
        "NO2": 0.011,
        "CO": 0.3,
        "SO2": 0.003,
        "IDEX_NM": "보통",
        "IDEX_MVL": 52,
        "ARPLT_MAIN": "O3"
      },
...
```

### 개발자 도쿠 콘솔에 도봉구 미세먼지 값 찍어보기
```
$.ajax({
  type: "GET",
  url: "http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99",
  data: {},
  success: function(response) {
		// 값 중 도봉구의 미세먼지 값만 가져와보기
		let dobong = response["RealtimeCityAir"]["row"][11];
		let gu_name = dobong['MSRSTE_NM'];
		let gu_mise = dobong['IDEX_MVL'];
		console.log(gu_name, gu_mise);
  }
});
```
![image](https://github.com/user-attachments/assets/c39292aa-01b8-409f-98d0-27858ea5651f)

### 모든 구의 미세먼지 값을 찍어보기
```
$.ajax({
  type: "GET",
  url: "http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99",
  data: {},
  success: function(response) {
    let mise_list = response["RealtimeCityAir"]["row"];
    for (let i = 0; i < mise_list.length; i++) {
      let mise = mise_list[i];
      let gu_name = mise["MSRSTE_NM"];
      let gu_mise = mise["IDEX_MVL"];
      console.log(gu_name, gu_mise);
    }
  },
});
```
혹은 (forEach)
```
$.ajax({
  type: "GET",
  url: "http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99",
  data: {},
  success: function(response) {
    let mise_list = response["RealtimeCityAir"]["row"];
    mise_list.forEach((mise) => {
      let gu_name = mise["MSRSTE_NM"];
      let gu_mise = mise["IDEX_MVL"];
      console.log(gu_name, gu_mise);
    });
  },
});
```
![image](https://github.com/user-attachments/assets/5b5080bd-986e-44c3-8705-42eda381abf2)
