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
