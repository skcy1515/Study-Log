# jQuery란?
HTML 요소들을 조작하는 편리한 자바스크립트 라이브러리

jQuery를 사용하기 위해서는 body 마지막 부분에 다음 코드를 추가하면 된다.
```
<script
  src="https://code.jquery.com/jquery-3.5.1.min.js"
  integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
  crossorigin="anonymous"
></script>
```

CSS와 마찬가지로 jQuery도 쓸 때 특정 요소를 '가리켜야' 조작할 수 있습니다. CSS에서는 선택자로 class를 주로 사용했는데, jQuery에서는 고유한 하나의 요소를 가리키는 id를 주로 사용합니다.

여러 요소에 동일한 class를 사용할 수 있는 반면, id는 하나의 요소에만 사용할 수 있습니다.

# 자주 쓰이는 jQuery 다뤄보기
## input 박스의 값 가져오기
![image](https://github.com/user-attachments/assets/7b762aed-ab7c-4265-a7ad-95d3bb29214d)
![image](https://github.com/user-attachments/assets/c31e7ce5-c345-4687-9feb-34ac3210b0be)

개발자 도구의 콘솔창에 다음 코드를 입력한다.
```
// id 값이 post-url인 요소를 선택하고, val()로 그 값을 가져옵니다.
let url = $('#post-url').val();
url // input 박스 안에 적었던 내용이 출력됩니다.
```

input 박스에 적혀있는 글을 바꾸고 싶다면 아래와 같은 코드를 쓸 수도 있습니다.
```
$('#post-url').val("값을 바꿔볼까요?");
```

## div 숨기기 / 보이기
```
// id 값이 post-box인 요소를 선택하고, hide()로 안 보이게 합니다.(=css의 display 값을 none으로 바꾸는 처리)
$('#post-box').hide();

// 이번에는 show()로 보이게 합니다.(=css의 display 값을 block으로 바꾸는 처리)
$('#post-box').show();
```

## CSS의 속성 값 가져오기
css()를 통해, hide() 또는 show()로 인해 변하는 CSS의 display 속성을 콘솔창에 출력할 수 있습니다.

```
$('#post-box').hide();
$('#post-box').css('display'); // none

$('#post-box').show();
$('#post-box').css('display'); // block
```

## 태그 내 텍스트 입력하기
- 위에서 본 것처럼 input 박스 안에는 .val() 메서드를 이용하여 값을 입력할 수 있습니다.
- 그 외의 경우에는 대부분 시작 태그와 종료 태그 사이에 있는 텍스트가 화면에 표시되며, 이 값은 아래와 같이 .text() 메서드를 이용하여 접근할 수 있습니다.

```
let btn_text = $('#btn-posting-box').text(); 
btn_text         // '포스팅박스 열기'
$('#btn-posting-box').text('포스팅박스 닫기');
```

## 태그 내 html 입력하기
포스팅하면 카드를 추가하는 등, 특정 태그 안에 새로운 html 요소를 동적으로 추가하고 싶을 때는 .append() 메서드를 사용할 수 있습니다.

### 텍스트 추가하기
![image](https://github.com/user-attachments/assets/98f7b2e2-089a-441d-8bce-658dfb03cc60)

```
$('#cards-box').append("추가 텍스트");
```

### 카드 추가하기
![image](https://github.com/user-attachments/assets/84d43f08-b46e-4e76-91c6-24c22b6c7c1d)

```
// 주의: 홑따옴표(')가 아닌 backtick(`)으로 감싸야 합니다. (템플릿 리터럴(Template Literals))
// 줄바꿈이나 문자열 중간에 다른 변수에 저장된 값을 삽입할 수 있습니다.
let img_url = 'https://cdn.vox-cdn.com/thumbor/Pkmq1nm3skO0-j693JTMd7RL0Zk=/0x0:2012x1341/1200x800/filters:focal(0x0:2012x1341)/cdn.vox-cdn.com/uploads/chorus_image/image/47070706/google2.0.0.jpg';
let link_url = 'https://google.com/';
let title = '제목 - 구글';
let desc = '구글에 대한 설명이 여기에 들어간다.';
let comment = '여기는 개인적인 코멘트가 들어간다.';

let temp_html = `<div class="card">
                    <img class="card-img-top"
                        src="${img_url}"
                        alt="Card image cap">
                    <div class="card-body">
                        <a href="${link_url}" class="card-title">${title}</a>
                        <p class="card-text">${desc}</p>
                        <p class="card-text comment">${comment}</p>
                    </div>
                </div>`;
$('#cards-box').append(temp_html);
```

## 페이지 로딩이 완료되면 실행하기

만약 어떤 기능이 페이지가 로딩되자마자 실행되기를 바란다면? 아래와 같이 써 줄 수 있습니다.
```
<script>

$(document).ready(function(){
	alert('페이지가 로딩되었습니다.')
});

</script>
```

# 실습 1. 나홀로 링크 메모장에 포스팅박스 열기/닫기 기능 붙이기
'포스팅 박스 닫기' 버튼을 누르면 포스팅 박스가 사라지고 글씨가 포스팅박스 열기로 바뀌고, 다시 버튼을 누르면 포스팅 박스가 열리고 글씨가 포스팅박스 닫기로 바뀌는 기능을 만들 수 있다.
```
    <script>
      function openclose() {
        let status = $("#post-box").css("display"); // 포스팅 박스 상태 확인 (기본 none)

        if (status === "block") {
          console.log("closed");
          $("#post-box").hide();
        } else {
          console.log("opened");
          $("#post-box").show();
        }
      }
    </script>
```

# 실습 2. 자바스크립트 & jQuery 같이 쓰기 (practice3.html)
![image](https://github.com/user-attachments/assets/1f755993-6e2d-4323-b5de-8be1585412c8)

```
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <title>jQuery 연습하고 가기!</title>

    <style type="text/css">
      div.question-box {
        margin: 10px 0 20px 0;
      }
    </style>

    <script>
      function q1() {
        // 1. input-q1의 입력값을 가져온다.
        // 2. 만약 입력값이 빈칸이면 if(입력값=='')
        // 3. alert('입력하세요!') 띄우기
        // 4. alert(입력값) 띄우기
        let text = $("#input-q1").val();
        if (text === "") {
          alert("입력하세요!");
        } else {
          alert(text);
        }
      }

      function q2() {
        // 1. input-q2 값을 가져온다.
        // 2. 만약 가져온 값에 @가 있으면 (includes 이용하기 - 찾아보자!)
        // 3. info.spartacoding@gmail.com -> gmail만 추출해서
        // 4. alert(도메인 값);으로 띄우기
        // 5. 만약 이메일이 아니면 '이메일이 아닙니다.'라는 경고창 띄우기
        let email = $("#input-q2").val();
        if (email.includes("@") === true) {
          alert(email);
        } else {
          alert("이메일이 아닙니다");
        }
      }

      function q3() {
        // 1. input-q3 값을 가져온다.
        // 2. 가져온 값을 이용해 names-q3에 붙일 태그를 만든다. (let temp_html = '<li>임꺽정</li>')
        // 3. 만들어둔 temp_html을 names-q3에 붙인다.(jQuery의 $('...').append(temp_html)을 이용하면 굿!)
        let name = $("#input-q3").val();
        if (name === "") {
          alert("이름을 입력하세요!");
        } else {
          let result = `<li>${name}</li>`;
          $("#names-q3").append(result);
        }
      }

      function q3_remove() {
        // 1. names-q3의 내부 태그를 모두 비운다.(jQuery의 $('....').empty()를 이용하면 굿!)
        $("#names-q3").empty("");
      }
    </script>
  </head>

  <body>
    <h1>jQuery + Javascript의 조합을 연습하자!</h1>

    <div class="question-box">
      <h2>1. 빈칸 체크 함수 만들기</h2>
      <h5>1-1. 버튼을 눌렀을 때 입력한 글자로 얼럿 띄우기</h5>
      <h5>
        [완성본]1-2. 버튼을 눌렀을 때 칸에 아무것도 없으면 "입력하세요!" 얼럿
        띄우기
      </h5>
      <input id="input-q1" type="text" /> <button onclick="q1()">클릭</button>
    </div>
    <hr />
    <div class="question-box">
      <h2>2. 이메일 판별 함수 만들기</h2>
      <h5>2-1. 버튼을 눌렀을 때 입력받은 이메일로 얼럿 띄우기</h5>
      <h5>
        2-2. 이메일이 아니면(@가 없으면) '이메일이 아닙니다'라는 얼럿 띄우기
      </h5>
      <h5>[완성본]2-3. 이메일 도메인만 얼럿 띄우기</h5>
      <input id="input-q2" type="text" /> <button onclick="q2()">클릭</button>
    </div>
    <hr />
    <div class="question-box">
      <h2>3. HTML 붙이기/지우기 연습</h2>
      <h5>3-1. 이름을 입력하면 아래 나오게 하기</h5>
      <h5>[완성본]3-2. 다지우기 버튼을 만들기</h5>
      <input id="input-q3" type="text" placeholder="여기에 이름을 입력" />
      <button onclick="q3()">이름 붙이기</button>
      <button onclick="q3_remove()">다지우기</button>
      <ul id="names-q3">
        <li>세종대왕</li>
        <li>임꺽정</li>
      </ul>
    </div>

    <!-- 이 부분은 절대 지우지 마세요. -->
    <script
      src="https://code.jquery.com/jquery-3.5.1.min.js"
      integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
      crossorigin="anonymous"
    ></script>
    <!-- 이 부분은 절대 지우지 마세요. -->
  </body>
</html>
```
