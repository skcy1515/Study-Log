# DB의 종류
Database는 크게 두 가지 종류로 나뉩니다.

- RDB은 행/열의 생김새가 정해진 엑셀에 데이터를 저장하는 것과 유사합니다. 데이터 50만 개가 적재된 상태에서, 갑자기 중간에 열을 하나 더하기는 어렵지만, 정형화되어 있는 만큼 데이터가 일관적이고 분석에 용이합니다. MS-SQL, My-SQL 등이 여기에 속하죠.
- NoSQL은 데이터를 다양한 형태로 저장할 수 있는 데이터베이스로, Document형, Graph형, Key-Value형, 시계열형 등 여러 유형이 있습니다. 우리가 사용할 MongoDB는 Document형 NoSQL DB 중 하나로, 데이터를 문서(Document) 형태로 저장합니다. MongoDB는 각 데이터가 같은 필드 값을 가질 필요가 없어 자유로운 형태의 데이터 저장에 유리합니다.

# MongoDB 이해하기
MongoDB는 데이터를 저장하고 관리할 수 있는 멋진 저장소예요! 데이터를 정리하기 위해 아래와 같은 구조를 사용해요

- Database (데이터베이스)
  - 데이터를 담는 가장 큰 상자예요.
  - 여러 개의 Collection(컬렉션)을 포함하고 있어요!
- Collection (컬렉션)
  - 데이터를 묶어두는 작은 상자들이에요.
  - 각 컬렉션 안에는 여러 개의 Document(도큐먼트)가 있어요.
- Document (도큐먼트)
  - MongoDB에서 실제 정보를 담고 있는 데이터 덩어리예요.
  - 우리가 필요한 내용은 모두 도큐먼트에 들어 있어요!

# MongoDB 사용하기
https://cloud.mongodb.com/v2#/org/67a2eb864bd38a79b8d2bf48/projects

1. Create 을 눌러 클러스터 생성하기
2. 무료 버전인 M0 를 선택, 이름과 프로바이더, 리전을 선택하여 Create Deployment 클릭
3. 꼭! Username과 Password를 복사해서 저장해둡니다. 이후 MongoDB Atlas에 연결할 때 사용됩니다. (skcy151515, IyuTp1jwPnkfLXXl)
4. Network Access를 눌러주세요. MongoDB Atlas를 어디서든 연결할 수 있게 할 거예요. ADD IP ADDRESS를 누르고, ALLOW ACCESS FROM ANYWHERE를 선택해주세요.

## MongoDB Atlas 콘솔: Database, Collection 만들기
- Clusters에서 여러분이 Deployment 한 Cluster를 볼 수 있어요
- Browse Collections 버튼을 눌러서 MongoDB에 저장된 데이터들을 확인할 수 있습니다
- ![image](https://github.com/user-attachments/assets/95a51b22-1a5a-407c-89d8-df3642c3a964)
- 데이터베이스 생성하기: Create Database를 눌러서 새로운 Database를 만들 수 있어요

## MongoDB Atlas 콘솔: 데이터 수동으로 넣기(INSERT)하기
- ![image](https://github.com/user-attachments/assets/518f84ba-8b62-4fcd-95ae-cf5b23026caa)
- 문서를 넣을 수 있습니다. 원하는 Database와 Collection을 선택한 후 INSERT DOCUMENT 버튼을 눌러주세요.
- ![image](https://github.com/user-attachments/assets/8c66bd46-e89b-48f1-82c0-129d4506e81a)
- 이후 원하는 데이터의 이름(key)과 값(value)를 넣어주면 됩니다.

# MongoDB Compass 설치하기
downloads.mongodb.com
https://downloads.mongodb.com/compass/mongodb-compass-1.45.0-win32-x64.exe

# pymongo로 MongoDB 조작하기
## DB를 연결하기 위해 URI 찾아보기
### 1. Atlas의 Clusters 에 들어가서 Connect를 눌러줍니다.
![image](https://github.com/user-attachments/assets/a936c845-c97b-475d-becb-1400ffa5e0a3)

### 2. 페이지 위에 뜨는 모달에 Drivers를 눌러주세요
![image](https://github.com/user-attachments/assets/38779660-05f4-4ff8-98ea-47d39a86d1d3)

### 3. 아래 이미지의 빨간색으로 표시해 둔 부분을 확인하시면 URI를 확인하실 수 있어요. 복사한 후에. 해당 URI의 <db_password> 부분에 알맞은 비밀번호를 채워넣어 주세요.(위에서 복사해둔 비밀번호를 채우면 됩니다)
![image](https://github.com/user-attachments/assets/d8cda644-165c-4ce4-a620-fefdf0067776)

```
python -m pip install "pymongo[srv]"
```
패키지 깔기

```
uri = "mongodb+srv://skcy151515:IyuTp1jwPnkfLXXl@cluster0.es5up.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0&tlsAllowInvalidCertificates=true"
```
URI 링크 (맨 뒤에 &tlsAllowInvalidCertificates=true 를 추가해주세요)

### 4. 새 파이썬 파일을 만들어 아래 코드를 붙여 넣어주세요.
```
from pymongo import MongoClient
# 아래 uri를 복사해둔 uri로 수정하기
uri = "mongodb+srv://skcy151515:IyuTp1jwPnkfLXXl@cluster0.es5up.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0&tlsAllowInvalidCertificates=true"
client = MongoClient(uri, 27017)   # MongoDB는 27017 포트로 돌아갑니다.
db = client.jungle # 'jungle'라는 이름의 db를 만듭니다.
```

### 5. DB 연결하기 & 데이터 넣기
![image](https://github.com/user-attachments/assets/6fc7ea8a-ae4a-4e93-a4f1-9326ac756e75)

```
from pymongo import MongoClient
# 아래 uri를 복사해둔 uri로 수정하기
uri = "mongodb+srv://skcy151515:IyuTp1jwPnkfLXXl@cluster0.es5up.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0&tlsAllowInvalidCertificates=true"
client = MongoClient(uri, 27017)   # MongoDB는 27017 포트로 돌아갑니다.
db = client.jungle # 'jungle'라는 이름의 db를 만듭니다.

# MongoDB에 insert 하기
    
# 'users'라는 collection에 {'name':'bobby','age':21}를 넣습니다.
db.users.insert_one({'name':'bobby','age':21})
db.users.insert_one({'name':'kay','age':27})
db.users.insert_one({'name':'john','age':30})
```

### 6. 모든 결괏값 보기
![image](https://github.com/user-attachments/assets/57794eed-2183-42ad-96ed-487c85611da0)

```
from pymongo import MongoClient
# 아래 uri를 복사해둔 uri로 수정하기
uri = "mongodb+srv://skcy151515:IyuTp1jwPnkfLXXl@cluster0.es5up.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0&tlsAllowInvalidCertificates=true"
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
```

### 7. 특정 결괏값을 뽑아 보기
```
user = db.users.find_one({'name':'bobby'})
print(user)
    
# 그중 특정 키 값을 빼고 보기
user = db.users.find_one({'name':'bobby'},{'_id':False})
print(user)
```

### 8. 수정하기
```
# 생김새 - db.people.update_many(찾을조건,{ '$set': 어떻게바꿀지 })
    
# 예시 - 오타가 많으니 이 줄을 복사해서 씁시다!
db.users.update_one({'name':'bobby'},{'$set':{'age':19}})
    
user = db.users.find_one({'name':'bobby'})
print(user)
```

### 9. 삭제하기 (거의 안 씀)
```
db.users.delete_one({'name':'bobby'})
    
user = db.users.find_one({'name':'bobby'})
print(user)
```

## pymongo 사용법 요약 정리
```
# 저장 - 예시
doc = {'name':'bobby','age':21}
db.users.insert_one(doc)
    
# 한 개 찾기 - 예시
user = db.users.find_one({'name':'bobby'})
    
# 여러 개 찾기 - 예시 ( _id 값은 제외하고 출력)
same_ages = list(db.users.find({'age':21},{'_id':False}))
    
# 바꾸기 - 예시
db.users.update_one({'name':'bobby'},{'$set':{'age':19}})
    
# 지우기 - 예시
db.users.delete_one({'name':'bobby'})
```

# MongoDBCompass 실행
1. Add new connection 버튼을 눌러주세요
![image](https://github.com/user-attachments/assets/b0da442a-485c-44af-abfd-e6f116480d8b)
2. URL 칸에 위에서 저장해둔 uri 입력, name 칸에 db_jungle 입력, Color 칸은 자유롭게 설정하고 Save & Connect 클릭해 주세요

