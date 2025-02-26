# 웹 스크래핑 결과를 DB에 저장하기 (5.py)
이 코드에서 시작

```
import requests
from bs4 import BeautifulSoup

def insert_all():
    # URL을 읽어서 HTML를 받아오고,
    headers = {'User-Agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36'}
    data = requests.get('https://www.imdb.com/chart/top/?ref_=nv_mv_250', headers=headers)
    
    # HTML을 BeautifulSoup이라는 라이브러리를 활용해 검색하기 용이한 상태로 만듦
    soup = BeautifulSoup(data.text, 'html.parser')

    # select를 이용해서, li들을 불러오기
    movies = soup.select('.sc-35aa5141-3.bnEgLu.ipc-page-grid__item.ipc-page-grid__item--span-2 > .ipc-metadata-list.ipc-metadata-list--dividers-between.sc-a1e81754-0.iyTDQy.compact-list-view.ipc-metadata-list--base > li')
    print(len(movies))

    # movies (li들) 의 반복문을 돌리기
    for movie in movies:
        # movie 안에 a 가 있으면,
        # (조건을 만족하는 첫 번째 요소, 없으면 None을 반환한다.)
        tag_element = movie.select_one('.ipc-title-link-wrapper > h3')
        if not tag_element:
            continue

        # 영화번호.영화 제목 가져오기.
        # 영화 타이틀은 "1. 쇼생크 탈출"과 같이  ". "으로 
        # 구분되어 있으므로 ". " 을 기준으로 split 한 뒤 index가 1인 요소에 접근한다.
        title = tag_element.text.split(". ", 1)[1]

        # 영화 개봉 연도 가져오기
        released_year = movie.select_one('.sc-300a8231-7.eaXxft.cli-title-metadata-item:nth-child(1)').text
        # 영화 개봉 연도의 타입을 int로 변환
        released_year = int(released_year)

        # 영화 상영시간 가져오기
        running_time = movie.select_one('.sc-300a8231-7.eaXxft.cli-title-metadata-item:nth-child(2)').text

        # 영화 상영시간 분으로 변환하기
        # 1. running_time에서 'h'와 'm'을 제거
        running_time = running_time.replace("h", "").replace("m", "")  # "2 55"로 변경

        # 2. 공백으로 나누기
        hours, minutes = running_time.split(" ")  # "2"와 "55"로 분리

        # 3. 문자열을 숫자로 변환
        hours = int(hours)
        minutes = int(minutes)

        # 4. 시간과 분을 계산
        running_time_minutes = hours * 60 + minutes

        # 영상물 등급 가져오기
        pg_level = movie.select_one('.sc-300a8231-7.eaXxft.cli-title-metadata-item:nth-child(3)').text

        print("완료", title, released_year, running_time_minutes, pg_level)

if __name__ == '__main__':
    # 영화 사이트를 scraping 해서 db에 채우기
    insert_all()
```

## 1. 코드에서 import 줄들 아래에 pymongo 기본 세팅 코드를 붙여 넣어주세요.
```
from pymongo import MongoClient           # pymongo를 임포트 하기(패키지 인스톨 먼저 해야겠죠?)
# 아래 uri를 복사해둔 uri로 수정하기
uri = 
client = MongoClient(uri, 27017)  # MongoDB는 27017 포트로 돌아갑니다.
db = client.dbjungle                      # 'dbjungle'라는 이름의 db를 만듭니다.
```

## 2. 각 영화마다 도큐먼트를 만들어서 하나씩 insert 해주세요.
```
from pymongo import MongoClient           # pymongo를 임포트 하기(패키지 인스톨 먼저 해야겠죠?)
# 아래 uri를 복사해둔 uri로 수정하기
uri = "mongodb+srv://<아이디>:<비밀번호>@cluster0.<unique>.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0&tlsAllowInvalidCertificates=true"
client = MongoClient(uri, 27017)  # MongoDB는 27017 포트로 돌아갑니다.
db = client.dbjungle                      # 'dbjungle'라는 이름의 db를 만듭니다.
```

## 3. MongoDB Compass에서 결과 확인하기
![image](https://github.com/user-attachments/assets/4d846bfc-587d-402d-b19b-5d03f5128806)

# find, update 연습하기 (6.py)
```
from pymongo import MongoClient
# 아래 uri를 복사해둔 uri로 수정하기
uri = 
client = MongoClient(uri, 27017)  # MongoDB는 27017 포트로 돌아갑니다.
db = client.dbjungle 

# 영화 제목 ‘포레스트 검프'의 개봉 연도를 가져오기
target_movie = db.movies.find_one({'title':'포레스트 검프'})
print (target_movie['released_year'])

# '포레스트 검프'와 같은 월에 개봉한 영화 제목들을 가져오기
target_year = target_movie['released_year']

movies = list(db.movies.find({'released_year':target_year}))

for movie in movies:
    print(movie['title'])

# 매트릭스 영화의 개봉 연도를 1998년으로 만들기
db.movies.update_one({'title':'매트릭스'},{'$set':{'released_year': 1998}})
```
