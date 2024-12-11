# 목차
- [SELECT](#select)
  - [FROM](#from)
  - [WHERE](#where)
  - [ORDER BY](#order-by)
- [INSERT](#insert)
- [UPDATE](#update)
- [DELETE](#delete)
- [집계 함수](#집계-함수)
  - [COUNT(*), COUNT(속성명)](#count-count속성명)
  - [SUM(속성명)](#sum속성명)
  - [AVG(속성명)](#avg속성명)
  - [MAX(속성명) / MIN(속성명)](#max속성명--min속성명)
  - [GROUP BY](#group-by)
    - [HAVING](#having)
- [조인](#조인)
  - [내부 조인](#내부-조인)
  - [외부 조인](#외부-조인)
- [부속 질의](#부속-질의)
  - [SELECT 절에서 사용](#select-절에서-사용)
  - [FROM 절에서 사용](#from-절에서-사용)
  - [WHERE 절에서 사용 (단일 행 부속질의)](#where-절에서-사용-단일-행-부속질의)
  - [WHERE 절에서 사용 (다중 행 부속질의)](#where-절에서-사용-다중-행-부속질의)
  - [WHERE 절에서 사용 (다중 열 부속질의)](#where-절에서-사용-다중-열-부속질의)
- [집합 연산](#집합-연산)
  - [UNION (합집합)](#union-합집합)
  - [INTERSECT (교집합)](#intersect-교집합)
  - [MINUS (차집합)](#minus-차집합)
- [검색 예시](#검색-예시)

# SELECT
관계 데이터베이스에서 정보를 검색하는 SQL문, 테이블에서 행과 열의 일부를 추출

기본 문법

```
SELECT [DISTINCT] 속성이름
FROM 테이블이름
// SELECT FROM 까지는 필수 나머지[]는 선택
[WHERE 검색조건]
[GROUP BY 속성이름]
[HAVING 검색조건]
[ORDER BY 속성이름 [ASC┃DESC]] 
```

## FROM
- SELECT * FROM 테이블 이름: 릴레이션에 있는 모든 속성 검색
- SELECT 속성 이름1, 속성 이름2 FROM 테이블 이름: 열들의 목록 지정
- SELECT 속성 이름1, 속성 이름2*12 FROM 테이블 이름: 열의 산술연산
- SELECT DISTINCT 속성이름 FROM 테이블 이름: 중복된 투플들을 하나씩만 나타나게 함

## WHERE
![image](https://github.com/user-attachments/assets/a750d4bf-c58f-46c0-8c8a-f52687a583c5)

- WHERE 속성명 BETWEEN 최소값 AND 최대값: 최소 값과 최대 값 사이의 범위에 들어가는지 여부파악, 최소 값과 최대 값도 범위에 포함됨
- WHERE 속성명 IN (숫자 값, '문자'): 목록 안의 값 중 하나(OR)와 일치하는지 여부를 파악하는데 사용
- WHERE 속성명 LIKE 패턴: 소량의 문자열 검색에 사용
  - % : 0개 이상의 문자와 일치한다.
    - LIKE 'abc%': abc로 시작하는 모든 문자열과 일치한다. abc, abcdef, abc123 등이 일치
    - LIKE '%abc': abc로 끝나는 모든 문자열과 일치한다. abc, 123abc, xyzabc 등이 일치
    - LIKE '%abc%': abc가 포함된 모든 문자열과 일치한다. abc, xyzabc, 123abc456 등이 일치
  - _ : 정확히 1개의 문자와 일치한다.
    - LIKE '_bc': 두 번째와 세 번째 자리가 b와 c인 모든 문자열과 일치한다. abc, 1bc, 2bc 등이 일치
    - LIKE 'a_c': 첫 번째 문자가 a, 두 번째 문자가 임의의 문자, 세 번째 문자가 c인 문자열과 일치한다. abc, acc, axc 등이 일치
  - %와 _의 결합 사용 예시
    -  LIKE ‘_a%’: 첫 번째 문자가 임의의 문자, 두 번째 문자가 a로 시작하는 문자열과 일치한다. bac, ba등이 일
    -  LIKE 'a_%_c': 첫 번째 문자가 a, 그 뒤에 두 개 이상의 문자가 오고, 마지막 문자가 c인 문자열과 일치한다. abc, axyc, a123c 등이 일치
- WHERE 속성명 IS / IS NOT: NULL에 대한 연산

## ORDER BY
행들을 특정 속성(들)을 기준으로 정렬하고자 할 때 ORDER BY 절을 사용

- ORDER BY 속성1 [ASC | DESC], 속성2 [ASC | DESC]: ASC는 오름차순 정렬, DESC는 내림차순 정렬, 기본적으로 오름차순이므로 ASC는 생략 가능
- 속성명이 여러 개일 때는 첫 번째 것이 1차 정렬 키, 두 번째것이 2차 정렬 키 등과 같이 적용

# INSERT
테이블에 새로운 투플을 삽입하는 명령

기본 문법

```
INSERT INTO 테이블이름 [(속성1, 속성2...)]
VALUES ( 값1, 값2... )
```

예:

```
INSERT INTO emp
VALUES (9001, '디비야', 'SE', 7698, NULL, NULL, NULL, NULL)
```

```
INSERT INTO emp(job, ename, empno)
VALUES ('SE', '김열공', 9002)
```

일부 속성의 값만 삽입, 속성과 값의 순서는 반드시 서로 일치해야 함, 아무 속성이나 생략할 수는 없음. NULL이 허용되지 않는(NOT NULL) 속성을 생략하면 오류 발생

예:

```
INSERT INTO emp (job, ename)
VALUES ('SE', '김에러')
```

# UPDATE 
특정 속성값을 수정하는 명령

기본 문법

```
UPDATE 테이블이름
SET 속성명1 = 값1 [ , 속성명2 = 값2, ... ]
[ WHERE 조건문 ]
```
WHERE 절은 생략할 수도 있지만, 이 경우 모든행을 일괄적으로 수정해 버리므로 주의해야 함 (따라서 대부분의 경우에 WHERE 절을 사용함)

예:

```
UPDATE emp
SET job = ‘CIO’
WHERE empno = 9001;
```

# DELETE
테이블에 있는 기존 투플을 삭제하는 명령

기본 문법

```
DELETE [FROM] 테이블이름
[ WHERE 조건문 ]
```
- 키워드 FROM 은 생략할 수 있음
- WHERE 절은 생략할 수도 있지만, 이 경우 모든 행을 일괄적으로 삭제해 버리므로 주의해야 함 (따라서 대부분의 경우에 WHERE 절을 사용함)
- DELETE 문이 실행되면 WHERE 절에서 지정한 해당 행 전체가 삭제됨 (행의 일부만 삭제할 수는 없음)

예:

```
DELETE FROM emp
WHERE empno = 9002
```

# 집계 함수
## COUNT(*), COUNT(속성명)
데이터의 총 개수 반환

예:

```
SELECT COUNT(*) as 총인원,
COUNT(COMM) as 총갯수 FROM emp -- 14, 4 반환
```

## SUM(속성명)
속성 값들의 합계 반환

예:

```
SELECT COUNT(COMM) as 총갯수,
SUM(COMM) as 총합 FROM emp -- 4, 2200 반환
```

## AVG(속성명)
속성 값들의 평균 반환

예:

```
SELECT COUNT(COMM) as 총갯수, SUM(COMM) as 합,
AVG(COMM) as 평균 FROM emp -- 4, 2200, 550 반환
```

## MAX(속성명) / MIN(속성명)
속성 값들 중에 최대값, 최소값 반환

예:

```
SELECT MAX(sal) as 최고급여,
MIN(sal) as 최저급여 FROM emp -- 5000, 800 반환
```

## GROUP BY
어떤 속성을 기준으로 집계 함수값(합계, 평균등)을 그 컬럼의 값 별로 보고자 할 때 사용

```
SELECT 속성명, 집계함수
FROM 테이블명
WHERE 조건
GROUP BY 속성명
```

![image](https://github.com/user-attachments/assets/bba1d2dd-e280-4532-95ae-eff388b77ea9)

### HAVING
그룹에 대한 조건, GROUP BY 절을 적용해서 나온 결과 값 중에서 원하는 조건에 부합하는 자료만 산출하고 싶을 때 사용

HAVING절 사용시 주의사항
- 반드시 GROUP BY 절과 함께 작성해야 함
- WHERE절보다 뒤에 나타나야 함
- HAVING 뒤에는 반드시 집계 함수가 와야 함

```
SELECT 속성명, 집계함수
FROM 테이블명
WHERE 검색조건
GROUP BY 속성명
HAVING 검색조건
```

![image](https://github.com/user-attachments/assets/656d28a5-96d0-4fe0-9dd7-8e6dd1ff8cf9)

![image](https://github.com/user-attachments/assets/f3da933b-4ac7-48bf-9507-6970683c3a18)

# 조인
1개 이상의 릴레이션으로부터 연관된 튜플을 결합하는 것, 속성들간 공통된 값(기본 키와 외래 키)을 사용하여 조인 실행

## 내부 조인
가장 일반적인 조인, 각 테이블에서 조인 조건을 만족하는 행들만 결과로 나타남

```
SELECT table.column1 [, table.column2, ….]
FROM table1, table2
WHERE table1.column1 = table2.column1 [and <검색조건>]
```

![image](https://github.com/user-attachments/assets/def082b2-2d96-4454-8ace-e107c544dfc8)

테이블 별칭: 테이블 별칭을 이용하여 긴 테이블 이름을 간단하게 사용, 테이블 이름 대신에 별칭 사용

```
SELECT e.empno,ename, job, d.dname, loc
FROM emp e INNER JOIN dept d
ON e.deptno = d.deptno
WHERE sal>=2000;
```

## 외부 조인
내부 조인은 조인하는 테이블의 두 개의 속성에서 공통된 값이 없다면 테이블로부터 행을 반환하지 않는다. 정상적으로 조인 조건을 만족하지 못하는 행들을 보기위해 외부 조인 사용

```
SELECT a.deptno, b.deptno
FROM emp a RIGHT OUTER JOIN dept b
ON a.deptno = b.deptno -- 오른쪽 외부 조인

SELECT a.deptno, b.deptno
FROM emp a LEFT OUTER JOIN dept b
ON a.deptno = b.deptno -- 왼쪽 외부 조인

SELECT a.deptno, b.deptno
FROM emp a FULL OUTER JOIN dept b
ON a.deptno = b.deptno -- 완전 외부 조인
```

# 부속 질의
하나의 SQL 문(주 질의 : main query)에 중첩된 SELECT 문
- select 절에서 사용 : 스칼라 부속질의
- from 절에서 사용 : 인라인 뷰
- where절에서 사용 : 중첩질의
  - 단일 행 (Single Row) 부속질의
  - 다중 행 (Multiple Rows) 부속질의
  - 다중 열 (Multiple Columns) 부속질의

## select 절에서 사용
각 사원의 급여와, 최대 급여를 받는 사원과 비교한 각 사원의 급여율을 검색하는 예

```
SELECT empno, sal, sal/(select max(sal) from emp)
FROM emp
```

## from 절에서 사용
![image](https://github.com/user-attachments/assets/464a5c45-1b56-4d94-bb0b-dadfe0b69e9d)

## where절에서 사용 (단일 행 부속질의)
오직 하나의 행을 반환, 단일 행 연산자(=,>, >=, <, <=, <>, !=) 만 사용가능

![image](https://github.com/user-attachments/assets/e42bdf6b-5076-46a8-937b-0d34e3000086)

## where절에서 사용 (다중 행 부속질의)
1개 이상의 행을 반환하는 부속질의, 복수 행 연산자(IN, NOT IN) 사용 가능

![image](https://github.com/user-attachments/assets/71c591fc-a711-467e-b7b8-d4f8c1f232b5)

## where절에서 사용 (다중 열 부속질의)
부속질의의 결과값이 2개 이상의 열을 반환하는 부속질의

![image](https://github.com/user-attachments/assets/b0464af8-fef2-469b-9a8a-bf71069f0822)

# 집합 연산
## UNION (합집합)

예:

```
SELECT deptno FROM emp
UNION
SELECT deptno FROM dept
```

## INTERSECT (교집합)

예:

```
SELECT deptno FROM emp
INTERSECT
SELECT deptno FROM dept
```

## MINUS (차집합)

예:

```
SELECT deptno FROM dept
MINUS
SELECT deptno FROM emp
```

# 검색 예시
student (student_number, student_name, student_year)

subject (subject_number, subject_name)

class (student_number, subject_number, class_year)

1. 과목이름이 ‘데이터베이스'인 과목의 과목번호를 검색하라.
```
SELECT subject_number 
FROM subject 
WHERE subject_name = '데이터베이스';
```

2. 학생의 이름이 ‘황’으로 시작하는 학생의 학번과 이름을검색하라.
```
SELECT student_number, student_name
FROM student
WHERE student_name LIKE '황%';
```

3. 과목번호 ‘1167’를 수강한 학생의 학번과 학생이름을 검색하라(학번 오름차순 정렬하여 검색할 것)

조인 사용
```
SELECT student.student_number, student_name
FROM student
INNER JOIN class
ON student.student_number = class.student_number
WHERE class.subject_number = 1167
ORDER BY student.student_number ASC;
```

부속 질의 사용
```
SELECT student_number, student_name
FROM student
WHERE student_number IN (
    SELECT student_number
    FROM class
    WHERE subject_number = '1167'
)
ORDER BY student_number ASC;
```

4. 어떤 학생도 수강하지 않은 과목의 과목번호를 검색하라
```
SELECT subject_number
FROM subject
WHERE subject_number NOT IN (
SELECT subject_number
FROM class
);
```

5. 2학년 학생이 수강한 과목의 과목번호와 과목이름을 검색하라. (주의사항 : 부속질의를 사용할 것)
```
SELECT subject_number, subject_name
FROM subject
WHERE subject_number IN (
SELECT class.subject_number
FROM class
INNER JOIN student
ON class.student_number = student.student_number
WHERE student.student_year = 2
);
```

6. 2024 년도에 2개 이상의 과목을 수강한 학생의 학번에 대해, 학번과 해당 학생이 수강한 교과목의 총 갯수를 검색하라.
```
SELECT c.student_number, COUNT(c.subject_number) AS total_subjects
FROM class c
WHERE c.class_year = 2024
GROUP BY c.student_number
HAVING COUNT(c.subject_number) >= 2;
```
