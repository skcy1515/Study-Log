import requests
from bs4 import BeautifulSoup
    
# 타겟 URL을 읽어서 HTML를 받아오고,
headers = {'User-Agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36'}
data = requests.get('https://www.imdb.com/chart/top/?ref_=nv_mv_250', headers=headers)
    
# HTML을 BeautifulSoup이라는 라이브러리를 활용해 검색하기 용이한 상태로 만듦
# soup이라는 변수에 "파싱 용이해진 html"이 담긴 상태가 됨
# 이제 코딩을 통해 필요한 부분을 추출하면 된다.
soup = BeautifulSoup(data.text, 'html.parser')

# select를 이용해서, li들을 불러오기
# class 명 앞에는 '.'을 붙여줍니다.
# class 명 내의 띄어쓰기(공백)은 '.'으로 바꾸어 써주세요.
# class는 맨 뒤에 것을 가져옴
movies = soup.select_one('#__next > main > div > div.ipc-page-content-container.ipc-page-content-container--center > section > div > div.ipc-page-grid.ipc-page-grid--bias-left > div > ul')

for movie in movies:
    tag_element = movie.select_one('.ipc-title__text')
    if not tag_element:
        continue
    
    # 영화번호.영화 제목 가져오기.
    print(tag_element.text)
    
    # 영화 개봉 연도 가져오기
    released_year = movie.select_one('.cli-title-metadata > span:nth-child(1)')
    print(released_year.text)
    
    # 영화 상영시간 가져오기
    running_time = movie.select_one('.cli-title-metadata > span:nth-child(2)')
    print(running_time.text)
    
    # 영상물 등급 가져오기
    pg_level = movie.select_one('.cli-title-metadata > span:nth-child(3)')
    print(pg_level.text)