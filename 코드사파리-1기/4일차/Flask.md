# Flask란?
파이썬으로 쓰인 웹 프레임워크로, 서버를 구동하는 데 필요한 여러 기능들을 제공합니다.

# Flask 기초: 서버 실행하기
```
pip install flask
```
터미널에서 플라스크 패키지 설치

```
from flask import Flask
app = Flask(__name__)

@app.route('/')
def home():
   return 'This is Home!'

if __name__ == '__main__':  
   app.run('0.0.0.0',port=5000,debug=True)
```
플라스크 시작 코드 붙여넣기 파일 이름은 상관없지만 통상적으로 flask 서버를 돌리는 파일은 app.py라고 이름 짓습니다.

이후 app.py가 담긴 폴더에 static, templates 폴더를 만들어줍니다.

이후 실행시켜서 http://localhost:5000/ 에 들어가서 This is Home! 화면이 뜨면 성공

끄는 건 터미널에서 Ctrl + C 누르기

# Flask 기초: URL 나누기
```
from flask import Flask
app = Flask(__name__)

@app.route('/')
def home():
   return 'This is Home!'

@app.route('/mypage')
def mypage():  
   return 'This is My Page!'

if __name__ == '__main__':  
   app.run('0.0.0.0',port=5000,debug=True)
```
위 코드를 이용하여 URL마다 다른 글씨를 띄우도록 해보겠습니다.

크롬 브라우저에서 http://localhost:5000/와 http://localhost:5000/mypage에 가보면 다른 글씨가 뜹니다.

# Flask 기초: HTML 파일 불러오기
templates 폴더는 HTML 파일을 담아두고 불러오는 역할을 합니다.

```
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script
      src="https://code.jquery.com/jquery-3.5.1.min.js"
      integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
      crossorigin="anonymous"
    ></script>
    <title>Document</title>
  </head>
  <body>
    <h1>서버를 만들었다!</h1>
  </body>
</html>
```
templates 폴더 안에 index.html을 만들고

```
from flask import Flask, render_template
app = Flask(__name__)

@app.route('/')
def home():
   return render_template('index.html')

@app.route('/mypage')
def mypage():  
   return 'This is My Page!'

if __name__ == '__main__':  
   app.run('0.0.0.0',port=5000,debug=True)
```
app.py에서 render_template 내장 함수를 통해 index.html과 연결한다.

![image](https://github.com/user-attachments/assets/7febd78a-c3f6-40b4-99e0-cd98b8806c78)

이후 처음으로 돌아와 html 파일이 제대로 적용되었는지 확인한다.

## Flask 기초: HTML 파일 내 이미지를 불러오기
static 폴더는 html 파일 외에 이미지, css 파일과 같은 파일을 담아두는 역할을 합니다.

static 폴더 안에 이미지를 넣어주고, 

```
<!-- Flask에서 띄울 때는 Flask에서 미리 정의된 방법으로 경로를 입력해 주어야 합니다. 그래서 static 폴더 안의 rome.jpg의 경로는 아래와 같이 써줍니다. -->
<img src="{{ url_for('static', filename='rome.jpg') }}" />
```
index.html 파일에 img 태그를 붙여넣어주면

![image](https://github.com/user-attachments/assets/0ce49c31-8e83-4aa7-b768-1432c73823f2)

이미지가 업로드 된다.

# API 만들기
예를 들어, 클라이언트에서 서버에 title_give란 키 값으로 데이터를 들고 왔다고 생각한다.

## GET 요청 API 코드
```
@app.route('/test', methods=['GET'])
def test_get():
   title_receive = request.args.get('title_give')
   print(title_receive)
   return jsonify({'result':'success', 'msg': '이 요청은 GET!'})
```
- `request.args`: 클라이언트가 GET 요청 시 URL에 포함한 쿼리 파라미터를 가져온다.
  - 예를 들어, 클라이언트가 아래와 같은 URL로 요청을 보냈다고 가정한다.
```
http://127.0.0.1:5000/test?title_give=HelloWorld
```
  - 위 URL에서는 HelloWorld가 title_receive 변수에 저장된다.

## GET 요청 확인 Ajax 코드
```
$.ajax({
    type: "GET",
    url: "/test?title_give=봄날은간다",
    data: {},
    success: function(response){
       console.log(response)
    }
  })
```
- `url: "/test?title_give=봄날은간다"`: title_give라는 이름의 파라미터에 값으로 봄날은간다를 전달한다.

## POST 요청 API 코드
```
@app.route('/test', methods=['POST'])
def test_post():
   title_receive = request.form['title_give']
   print(title_receive)
   return jsonify({'result':'success', 'msg': '이 요청은 POST!'})
```

## POST 요청 확인 Ajax 코드
```
$.ajax({
    type: "POST",
    url: "/test",
    data: { title_give:'봄날은간다' },
    success: function(response){
       console.log(response)
    }
  })
```
