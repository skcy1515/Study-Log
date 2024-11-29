# 목차
- [CREATE TABLE](#create-table)
  - [데이터 타입](#데이터-타입)
  - [널값의 허용 여부](#널값의-허용-여부)
- [ALTER TABLE](#alter-table)
  - [ADD](#add)
  - [MODIFY](#modify)
  - [DROP COLUMN](#drop-column)
  - [DROP TABLE](#drop-table)
  - [ADD CONSTRAINT](#add-constraint)
  - [DROP CONSTRAINT](#drop-constraint)
- [무결성 제약 조건](#무결성-제약-조건)
  - [도메인 무결성](#도메인-무결성)
    - [NOT NULL](#not-null)
    - [CHECK](#check)
  - [개체 무결성](#개체-무결성)
    - [PRIMARY KEY](#primary-key)
    - [UNIQUE](#unique)
  - [참조 무결성](#참조-무결성)
    - [FOREIGN KEY](#foreign-key)
    - [FOREIGN KEY : CASCADE](#foreign-key--cascade)

# CREATE TABLE
테이블을 생성하는 문법

기본 문법

```
CREATE TABLE 테이블이름
( { 속성이름 데이터타입 [제약조건],}
{ [제약조건] }
)
```

데이터 타입
![image](https://github.com/user-attachments/assets/7eaf50ef-cb76-466f-aa4c-dda575b49cdd)

널값의 허용 여부 : NULL, NOT NULL
- NOT NULL : 널 값을 허용하지 않음(필수적입력)
- NULL : 널 값 허용(생략 가능)

예:

```
CREATE TABLE empl_1 (
  id NUMBER(5) NOT NULL,
  name VARCHAR2(10) NULL
);
```

# ALTER TABLE
생성된 테이블의 속성과 제약조건을 변경, 테이블에 이미 데이터가 있는 상태에서, 새로운 속성이 추가될 때는 추가된 속성의 값은 NULL 값이 된다.

기본 문법

```
ALTER TABLE 테이블이름
  [ADD 속성이름 데이터타입]
  [DROP COLUMN 속성이름]
  [MODIFY 속성이름 데이터타입]
  [ADD [CONSTRAINT 제약조건이름] 제약조건 (속성이름)]
  [DROP CONSTRAINT 제약조건이름]
```

## ADD
```
ALTER TABLE 테이블
ADD 속성명 데이터타입
```
새로운 속성을 추가한다. 

예:

```
ALTER TABLE empl_1
ADD address VARCHAR2(10) NULL;
```

## MODIFY
```
ALTER TABLE 테이블
MODIFY 속성명 데이터타입
```
속성의 타입을 변경한다.

예:

```
ALTER TABLE empl_1
MODIFY address VARCHAR2(20);
```

## DROP COLUMN
```
ALTER TABLE 테이블
DROP COLUMN 속성명
```
속성을 삭제한다.

예:

```
ALTER TABLE empl_1
DROP COLUMN address;
```

## DROP TABLE
```
DROP TABLE 테이블이름
```
테이블을 삭제한다.

## ADD [CONSTRAINT]
```
ALTER TABLE 테이블명
ADD [CONSTRAINT 제약조건이름]
제약조건 (컬럼명)
```
기존의 테이블에 새로운 제약 조건을 추가한다.

## DROP CONSTRAINT
```
ALTER TABLE 테이블명
DROP CONSTRAINT 제약조건이름
```
기존의 테이블에 대한 제약조건을 삭제한다.

# 무결성 제약 조건 
- 데이터 무결성: 데이터를 결함이 없는 상태, 즉 정확하고 유효하게 유지하는 것
- 무결성 제약조건: 데이터의 무결성을 보장하고 일관된 상태로 유지하기 위한 규칙
  - 무결성 제약 조건을 만족하는 경우 데이터베이스에 저장된 모든 데이터가 정확한 상태로 저장될 수 있음을 보장
  - 데이터베이스의 데이터에 어떤 변경이 있을 때, DBMS가 자동적으로 무결성 제약조건을 검사하므로 응용 프로그램들은 이러한 제약조건을 검사할 필요가 없음

![image](https://github.com/user-attachments/assets/62d9c079-1c2a-4cb6-8784-58c4ceb62e5c)

## 도메인 무결성
### NOT NULL
필수 입력을 위해, NULL값을 허용하지 않겠다는 의미
- 열을 정의할 때 NULL 을 지정하면 이 열에 데이터를 삽입 또는 갱신할 때 NULL 을 허용하겠다는 의미
- 제약조건을 명시하지 않으면 NULL 을 허용하는것으로 간주

### CHECK
데이터를 삽입하거나 수정할 때 열의 값이 정의된 규칙에 부합되는지를 검사하는 것
  - select문의 where에 들어가는 조건을 쓰면 됨
  - 예: CHECK (col >= 15000 AND col <= 100000)

예:

```
CREATE TABLE test_tbl3 (
  start_date char(10),
  end_date char(10),
  CONSTRAINT CHK_dates
    CHECK (start_date <= end_date)
);
INSERT INTO test_tbl3 VALUES ('2005/03/20', '2005/03/19'); -- 안 됨
INSERT INTO test_tbl3 VALUES ('2005/03/19', '2005/03/20'); -- 됨
```

## 개체 무결성
### PRIMARY KEY
기본 키 (primary key)의 특징 
- 테이블당 최대 하나의 기본키 제약조건만 설정.
- 기본 키의 값은 유일해야 하며, NULL을 허용하지 않는다.

예:

```
CREATE TABLE test_tbl4 (
  pk1 number(3) PRIMARY KEY, -- 기본 키 지정
  uq1 number(3) NULL
);


CREATE TABLE test_tbl5 (
  pk2 number(3) NOT NULL,
  fk2 number(3) NOT NULL,
  CONSTRAINT tbl5_pk PRIMARY KEY(pk2, fk2) -- pk2, fk2를 test_tbl5의 기본 키로 설정
);

INSERT INTO test_tbl4 (pk1) VALUES (1); -- 됨
INSERT INTO test_tbl5 (pk2) VALUES (1); -- 안 됨
```

### UNIQUE
유일성 제약 조건 특징
- 데이터의 유일성을 보장
- 중복되는 데이터가 존재할 수 없다.
- PRIMARY KEY와 유사하지만, NULL 을 허용한다.
- 한 테이블에 여러 개의 유일성 제약 조건 가능
- 주로 기본 키로 지정되지 않은 후보 키에 적용된다.

예:

```
SELECT * FROM test_tbl4;

ALTER TABLE test_tbl4
ADD CONSTRAINT uq_test_tbl4
UNIQUE (uq1);

INSERT INTO test_tbl4 VALUES (2, 10);
INSERT INTO test_tbl4 VALUES (3, 20);
INSERT INTO test_tbl4 VALUES (4, 20); -- 안 됨
```

## 참조 무결성
### FOREIGN KEY
릴레이션의 기본키를 참조하는 속성 (또는 속성 집합)
- 두 릴레이션의 연관된 투플들 사이의 일관성을 유지하는데 사용됨
- 릴레이션 R2의 외래 키가 릴레이션 R1의 기본 키를 참조할 때, 참조 무결성 제약조건은 아래의 두 조건 중 하나가 성립되면 만족됨
  - 외래 키의 값 : R1의 어떤 투플의 기본 키 값
  - 외래 키의 값 : 널 값
- 부모 릴레이션, 자식 릴레이션
  - 자식 릴레이션 : 외래 키를 가진 릴레이션
  - 부모 릴레이션 : 외래 키가 참조하는 기본 키를 가진 릴레이션
- 외래키 속성과 그것이 참조하는 기본키 속성의 이름은 달라도 되지만 도메인은 같아야 한다.

![image](https://github.com/user-attachments/assets/908d1a97-5627-48d7-89cb-90b2c9dfa7f3)

기본 문법

```
컬럼명 데이터형,
…….,
[CONSTRAINT 제약조건이름]
FOREIGN KEY (컬럼명 1 [, 컬럼명 2, ….])
REFERENCES 테이블명(컬럼명 1 [, 컬럼명 2, ….] [ON
DELETE CASCADE])
```

예(test_tbl6 테이블의 fk2를 test_tbl4의 pk1을 참조할수 있도록 외래 키 지정): 

```
CREATE TABLE test_tbl4 (
  pk1 number(3) PRIMARY KEY,
  uq1 number(3) NULL
); -- 이전에 만들어둔 테이블

CREATE TABLE test_tbl6 (
  pk2 number(3) NOT NULL,
  fk2 number(3) NOT NULL,
  CONSTRAINT fk_test_tbl6
  FOREIGN KEY (fk2)
  REFERENCES test_tbl4(pk1)
);
INSERT INTO test_tbl4 VALUES (1, 100);
INSERT INTO test_tbl4 VALUES (2, 200);

INSERT INTO test_tbl6 VALUES (300, 1);
INSERT INTO test_tbl6 VALUES (400, 2);
INSERT INTO test_tbl6 VALUES (500, 4); -- 오류
```

### FOREIGN KEY : CASCADE
참조되는 테이블(부모 테이블)의 데이터가 변동되었을 때 이를 참조하는 쪽(자식 테이블)에서도 자동으로 변동되도록 설정

만약 ON DELETE CASCADE 없이, 외래키가 있는데 부모의 기본 키를 삭제할 경우 에러가 뜬다.

![image](https://github.com/user-attachments/assets/3fc5a3ad-5438-49a6-9363-8ba1a59f2874)

예:

```
CREATE TABLE test_tbl7 (
  pk4 number(3) PRIMARY KEY -- primary key
);
  
CREATE TABLE test_tbl8 (
  pk5 number(3) PRIMARY KEY,
  fk5 number(3),
  CONSTRAINT test_tbl8_fk5
  FOREIGN KEY (fk5)
  REFERENCES test_tbl7 (pk4) ON DELETE CASCADE --연속 삭제 설정
);

INSERT INTO test_tbl7 (pk4) VALUES(1);
INSERT INTO test_tbl8 (pk5,fk5) VALUES (1,1);
INSERT INTO test_tbl8 (pk5,fk5) VALUES (2,1);

SELECT * FROM test_tbl7;
SELECT * FROM test_tbl8;

DELETE test_tbl7 WHERE pk4 = 1 -- 전부 삭제됨
```
