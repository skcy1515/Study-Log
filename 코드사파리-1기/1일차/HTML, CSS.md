# HTML
## HTML이란?
HTML은 'Hypertext Markup Language'의 줄임말로, 웹페이지의 구조와 내용을 정의하는 언어입니다. 브라우저는 HTML 코드를 해석해 글자, 이미지, 버튼 같은 요소들을 화면에 표시합니다.

- HTML 요소는 태그(tag)로 내용을 감싸 표현합니다. 예를 들어, 제목은 `<h1>` 태그로, 본문은 `<p>` 태그로 감싸서 나타냅니다.
![image](https://github.com/user-attachments/assets/cb256d28-0814-4c5d-9358-3ec12e2c4ac3)
각 태그들은 보통 시작 태그 <태그>와 종료 태그 </태그>의 한 쌍으로 이루어져 있습니다.

- 각 태그에는 style 속성을 사용해 크기, 정렬, 글꼴, 색 등을 꾸밀 수 있습니다.
![image](https://github.com/user-attachments/assets/35a53395-449f-4a50-8331-78b0ffafca38)
이렇게 style 속성을 사용하는 방법 외에도, CSS(Cascading Style Sheets)라는 것을 사용해서 HTML 요소에 스타일을 지정할 수도 있습니다.

## HTML 기초
HTML은 크게 head와 body로 나뉩니다.
![image](https://github.com/user-attachments/assets/2afb0afa-d90a-47db-b936-cafc9644736e)

- head 안에 들어가는 대표적인 요소들: meta, title, link, script 등
- body 안에 들어가는 대표적인 요소들: div, p, img 등

## HTML 기본 태그들
```
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>크래프톤 정글 | HTML 기초</title>
</head>

<body>
    <!-- 구역을 나누는 태그들 -->
    <div>div 태그는 구역을 나눈다.</div>
    <div>
        <p>p는 문단이다.</p>
        <p>문단 사이에는 줄바꿈이 일어난다.</p>
    </div>
    <ul>
        <li>ul 태그는 목록을 나타낸다.</li>
        <li>li 태그는 목록의 항목을 나타낸다.</li>
    </ul>

    <!-- 콘텐츠 태그들 -->
    <h1>h1은 제목</h1>
    <h2>h2는 소제목</h2>
    <h3>h3~h6으로 갈수록 글씨가 작아진다.</h3>
    <hr>
    가로줄인 hr 태그는 종료 태그가 따로 없는 empty 태그이다.
    <hr>
    span 태그는 요소 안에서 특정 <span style="color:red">글자</span>를 꾸밀 때 쓸 수 있다.
    <hr>
    a 태그: <a href="http://naver.com/"> 하이퍼링크 </a>
    <hr>
    img 태그: <img src="https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png" />
    <hr>
    input 태그: <input type="text" />
    <hr>
    button 태그: <button>버튼</button>
    <hr>
    textarea 태그: <textarea>여러 줄을 입력할 수 있다.</textarea>
</body>

</html>
```

# CSS
## CSS 기초
CSS는 Cascading Style Sheet의 약자입니다. 여기서 'Cascading'은 '폭포처럼 흐른다'는 뜻입니다. HTML에서 배운 부모-자식 구조를 기억하시죠? CSS는 이름 그대로 폭포처럼 위에서 아래로 스타일이 적용됩니다. 즉, 부모 요소에 스타일을 지정하면, 그 안의 자식 요소들에도 스타일이 자동으로 적용되는 것이죠.

### 여러 종류의 선택자들
```
/* 모든 태그에 적용하고 싶을 때 */
* {
    속성: 선택지;
}

/* 특정 태그를 지칭할 때 */
태그 {
    속성1: 선택지1;
    속성2: 선택지2;
}

/* 클래스 이름 앞에는 .을 찍는다. */
.클래스 {
    속성: 선택지;
}

/* 아이디 앞에는 #을 붙인다. */
#아이디 {
    속성: 선택지;
}

/* 특정 클래스를 갖는 특정 태그를 지칭할 때는 클래스를 뒤에 붙여 쓴다 */
태그.클래스 {
    속성: 선택지;
}

/* 자식 선택자는 > 뒤에 적는다 */
/* 즉, 아래는 '아이디'라는 아이디를 갖는 요소 바로 안의 '태그' 태그를 선택한 것이다 */
#아이디 > 태그 {
    속성: 선택지;
}

/* 자손 선택자는 띄어쓰기로 구분한다. */
/* 즉, 아래는 '클래스'라는 클래스를 갖는 '태그1' 태그 안에 있는 모든 '태그2'를 선택한 것이다 */
태그1.클래스 태그2 {
    속성: 선택지;
}
```

### 자주 쓰이는 스타일
```
[배경]
background-color
background-image
background-size

[사이즈]
width
height

[폰트]
font-size
font-weight
font-famliy
color (*주의: font-color가 아니라 그냥 color에요!)

[간격]
margin (바깥 여백)
padding (안쪽 여백)
```

## 연습 (login.html)
![image](https://github.com/user-attachments/assets/22b8229f-c587-4802-9d97-211105f3af3f)

```
<!DOCTYPE html>
<html lang="ko">
  <head>
    <title>로그인 페이지</title>
    <style>
      .page {
        margin: 10px auto;
        width: 300px;
        /* width가 없으면 블록 요소(div 등)는 기본적으로 부모 요소의 전체 너비를 차지하므로 auto 마진이 적용되지 않는다. */
        /* width가 존재하면 요소의 크기가 지정되므로 브라우저가 남은 공간을 자동으로 나누어 좌우 마진을 동일하게 만들어 중앙 정렬된다. */
      }

      .mytitle {
        width: 300px;
        height: 200px;

        background-image: url("https://www.ancient-origins.net/sites/default/files/field/image/Agesilaus-II-cover.jpg");
        background-position: center; /* 배경 이미지를 중앙 정렬한다. */
        background-size: cover; /* 배경 이미지를 요소 크기에 맞춰 조절한다. */

        border-radius: 10px;
        padding-top: 40px;

        color: white;
        text-align: center;
      }
    </style>
  </head>
  <body>
    <div class="page">
      <div class="mytitle">
        <h1>로그인 페이지</h1>
        <p1>아이디, 비밀번호를 입력해주세요</p1>
      </div>
      <div>
        <p>ID: <input type="text" /></p>
        <p>PW: <input type="text" /></p>
        <button>로그인하기</button>
      </div>
    </div>
  </body>
</html>
```

## 구글 웹폰트 입히기
1. https://fonts.google.com/?script=Kore 사이트에서, 마음에 드는 폰트를 골라 클릭
2. Get font -> Get embed code 클릭
3. 코드 복사 붙여넣기
![image](https://github.com/user-attachments/assets/4dbaeb13-ce95-4cc1-b33e-c5f6b643c577)

```
<!DOCTYPE html>
<html lang="ko">
  <head>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap"
      rel="stylesheet"
    />
    <title>로그인 페이지</title>
    <style>
      * {
        font-family: "Black Han Sans", serif;
        font-weight: 400;
        font-style: normal;
      }
```
![image](https://github.com/user-attachments/assets/ad0a906a-b381-4a97-a8d6-014b8defdcdf)

## CSS 분리
기존의 코드들을 style.css에 전부 옮기고 head에 코드 추가
```
    <link rel="stylesheet" type="text/css" href="style.css" />
```
