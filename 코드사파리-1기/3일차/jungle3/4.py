from pymongo import MongoClient
# 아래 uri를 복사해둔 uri로 수정하기
uri = 
client = MongoClient(uri, 27017)   # MongoDB는 27017 포트로 돌아갑니다.
db = client.jungle # 'jungle'라는 이름의 db를 만듭니다.

# MongoDB에 insert 하기
    
# MongoDB에서 데이터 모두 보기
all_users = list(db.users.find({}))
    
# 참고) MongoDB에서 특정 조건의 데이터 모두 보기
same_ages = list(db.users.find({'age':21}))

print(all_users[0])         # 0번째 결과값을 보기
print(all_users[0]['name']) # 0번째 결과값의 'name'을 보기

for user in all_users:      # 반복문을 돌며 모든 결과값을 보기
    print(user)

user = db.users.find_one({'name':'bobby'})
print(user)
    
# 그중 특정 키 값을 빼고 보기
user = db.users.find_one({'name':'bobby'},{'_id':False})
print(user)
