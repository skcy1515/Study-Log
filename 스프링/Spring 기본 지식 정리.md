# 스프링이란?
스프링은 엔터프라이즈용 자바 애플리케이션 개발을 편하게 할 수 있게 해주는 프레임워크이다.

- 엔터프라이즈 애플리케이션: 대규모의 복잡한 데이터를 관리하는 애플리케이션
- 프레임워크: 소프트웨어 개발을 수월하게 하기 위한 소프트웨어 개발 환경
- 라이브러리: 애플리케이션 개발 에 필요한 기능인 클래스 , 함수 등 을 모아 놓은 코드의 모음

# 스프링 부트란?
스프링 부트는 스프링 프레임 워크를 더 쉽고 빠르게 이용할 수 있도록 만들어주는 도구이다. 스프링 부트는 자동으로 개발 환경 구성을 해주고, WAS(web application server)를 자체적으로 가지고 있다.

![image](https://github.com/user-attachments/assets/37542d10-ae4b-420f-b035-8702d99acb30)

# 스프링의 주요 특징
### IoC (제어의 역전) / DI (의존성 주입)
- IoC(Inversion of Control)은 객체의 생성과 관리를 개발자가 하는 것이 아니라 프레임워크가 대신하는 것을 말한다.
- DI(Dependency Injection)은 IOC를 구현하는 방식으로, 외부에서 객체를 주입받아 사용하는 것을 말한다.

```
public class Car {
    private Engine engine;

    public Car() {
        // 직접 객체 생성
        this.engine = new Engine();
    }

    public void start() {
        engine.start();
    }
}
```
IoC가 없는 코드
- Car 클래스가 Engine 객체를 직접 생성하고 의존성을 관리한다.
- 이 경우 Car와 Engine 간의 강한 결합이 발생하며, 다른 구현체로 교체하기 어렵다.

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Engine {
    public void start() {
        System.out.println("Engine is starting...");
    }
}

@Component
public class Car {
    private final Engine engine;

    // 생성자 주입 방식 (DI 적용)
    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void start() {
        engine.start();
    }
}
```
Ioc / DI 적용 코드
- 여기서는 Car 객체가 Engine 객체를 직접 생성하지 않고, 스프링 컨테이너가 Engine 객체를 주입해준다.
- IOC: 스프링 컨테이너가 객체를 생성하고, 관리하며 의존성을 연결한다.
- DI: 스프링이 생성자를 통해 Car 클래스에 Engine 객체를 주입한다.

여기에서 사용하는 `@Autowired` 라는 애너테이션은 스프링 컨테이너에 있는 빈이라는 것을 주입하는 역할을 한다. 빈은 쉽게 말해 스프링 컨테이너에서 관리 하는 객체를 말한다.

### 빈과 스프링 컨테이너
스프링은 스프링 컨테이너를 제공한다. 스프링 컨테이너는 빈을 생성하고 관리한다. 즉, 빈이 생성되고 소멸되기까지의 생명주기를 이 스프링 컨테이너가 관리한다. 또한 개발자가 `@Autowired` 같은 애너테이션을 사용해 빈을 주입받을 수 있게 DI를 지원하기도 한다.

빈은 스프링 컨테이너가 생성하고 관리하는 객체이다. 스프링 부트에서 사용자 클래스를 스프링 빈으로 등록하는 가장 쉬운 방법은 클래스 선언부 위에 `@Component` 어노테이션을 사용하는 것이다. `@Controller`, `@Service`, `@Repository`는 모두 `@Component`를 포함하고 있으며 해당 어노테이션으로 등록된 클래스들은 스프링 컨테이너에 의해 자동으로 생성되어 스프링 빈으로 등록된다.

### AOP (관점 지향 프로그래밍)
AOP(Aspect Oriented Programming)는 프로그래밍을 할 때 핵심 관점과 부가 관점을 나누어서 개발하는 것을 말한다. 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화하겠다는 것이다. 여기서 모듈화란 어떤 공통된 로직이나 기능을 하나의 단위로 묶는 것을 말한다. 
 
예로들어 핵심적인 관점은 결국 우리가 적용하고자 하는 핵심 비즈니스 로직이 된다. 또한 부가적인 관점은 핵심 로직을 실행하기 위해서 행해지는 데이터베이스 연결, 로깅, 파일 입출력 등을 예로 들 수 있다.

### PSA (이식 가능한 서비스 추상화)
PSA(Portable Service Abstraction)은 스프링에서 제공하는 다양한 기술들을 추상화 해 개발자가 쉽게 사용하는 인터페이스를 말한다. 대표적인 PSA의 예로는 클라이언트의 매핑과 클래스, 메서드의 매핑을 위한 애너테이션이 있습니다. 또한 스프링에서 데이터베이스에 접근하기 위한 기술인 JPA가 있다.

# MVC 패턴
MVC (Model-View-Controller) 패턴은 애플리케이션을 Model, View, Controller 세 가지 역할로 구분해 개발하는 아키텍처 패턴이다. 
- Model (모델): 애플리케이션의 핵심 데이터와 비즈니스 로직을 담고 있다. 데이터베이스와의 연동을 담당하며, 상태를 관리한다.
    - 예: RestaurantEntity, MenuEntity 같은 JPA 엔티티 객체.
- View (뷰): 사용자에게 보여지는 화면(UI)을 담당한다. HTML, CSS, JavaScript 등으로 작성되며 사용자가 데이터를 입력하거나 결과를 확인할 수 있는 페이지를 제공한다.
    - 예: React, Vue.js 컴포넌트나 JSP, Thymeleaf 같은 템플릿 엔진.
- Controller (컨트롤러): 사용자의 요청을 처리하고, 모델과 뷰 사이를 연결하는 역할을 한다. 사용자의 요청을 받아 비즈니스 로직을 실행하고, 그 결과를 View로 반환한다.
    - 예: Spring Boot의 @Controller 클래스.

### MVC 흐름 및 데이터 전송 과정
1. 사용자가 View에 데이터 입력
2. View -> Controller로 Request 전송
	`@RequestBody`: 요청 데이터를 DTO 객체 (Request)로 매핑
3. Controller -> Service로 Request 전달
4. Service 계층에서 Entity로 데이터 매핑
5. Service 계층에서 생성한 Entity 객체를 Repository에 넘겨 데이터베이스에 저장
6. Repository에서 데이터베이스로 데이터 저장
7. Service에서 Controller로 Response 반환
8. Controller -> 클라이언트로 응답

# API와 REST API
API는 프로그램 간에 상호작용하기 위한 매개체를 말한다. 식당으로 예를 들면 손님은 클라이언트, 주방은 서버이며 직원(API)가 손님의 요청을 받고 주방에게 요청을 건네며, 주방에게 응답을 받아 다시 손님에게 응답을 건넨다.

REST API는 웹의 장점을 최대한 활용하는 API이다. REST는 다음과 같은 기본 원칙을 따른다
- 리소스 기반: REST API는 리소스를 URI(Uniform Resource Identifier)로 식별한다. 각 리소스는 고유한 URL을 가지며, 리소스는 데이터나 서비스로 이해할 수 있다.
- HTTP 메서드 사용: REST API는 HTTP 프로토콜의 메서드를 사용하여 리소스에 대한 작업을 수행한다. 일반적으로 사용되는 메서드는 다음과 같다:
	- GET: 리소스를 조회한다.
	- POST: 새로운 리소스를 생성한다.
	- PUT: 기존 리소스를 업데이트한다.
	- DELETE: 리소스를 삭제한다.
- 상태 없음: 각 요청은 독립적이며, 서버는 클라이언트의 상태를 저장하지 않는다. 클라이언트는 요청 시 필요한 모든 정보를 포함해야 한다.
- 표현: 리소스는 다양한 형태로 표현될 수 있으며, 일반적으로 JSON이나 XML 형식으로 데이터가 전송된다.

# JPA
JPA는 자바에서 객체-관계 매핑(ORM)을 제공하는 API이다. 즉, 자바 객체와 데이터베이스 테이블 간의 매핑을 통해, 데이터베이스에 대한 CRUD(Create, Read, Update, Delete) 작업을 간편하게 수행할 수 있게 도와준다.

ORM: 자바의 객체와 데이터베이스를 연결하는 프로그래밍 기법, SQL을 직접 작성하지 않고 사용하는 언어로 데이터베이스에 접근할 수 있다.

### 스프링 데이터 JPA
스프링 데이터 JPA는 JPA를 쉽게 사용할 수 있도록 도와주는 라이브러리이다. 스프링 데이터의 인터페이스인 PagingAndSortingRepository를 상속받아 JpaRepository 인터페이스를 만들었으며, JPA를 더 편리하게 사용하는 메서드를 제공한다. 

다음과 같이 JpaRepository 인터페이스를 우리가 만든 인터페이스에서 상속받고, 제네릭에는 관리할 <엔티티 이름, 엔티티 기본키의 타입>을 입력하면 기본 CRUD 메서드를 사용할 수 있다.

```
public interface MemberRepository extends JpaRepository<Member, Long>{
}
```

# 리포지토리
리포지토리는 데이터베이스와 직접 상호작용하는 계층으로, `@Repository` 어노테이션을 사용하여 정의한다. 보통 Spring Data JPA 같은 라이브러리를 사용하여 리포지토리를 쉽게 관리한다.

1. 데이터베이스 CRUD(Create, Read, Update, Delete) 작업을 수행한다.
2. JPA를 이용하면 기본적인 CRUD 기능을 자동으로 제공받을 수 있으며, 필요한 경우 커스텀 쿼리를 작성할 수도 있다.

# 컨트롤러
컨트롤러는 클라이언트의 요청을 받아들이고, 해당 요청을 처리할 비즈니스 로직을 서비스에 전달하는 역할을 한다. 컨트롤러는 주로 `@RestController` 어노테이션을 사용해 정의한다.

1. 클라이언트가 보낸 HTTP 요청을 받는다.
2. 요청에 따라 필요한 서비스 메서드를 호출하고, 결과를 클라이언트에게 반환한다.
3. 클라이언트가 웹 페이지를 요청하는 경우 `@Controller`를 사용하고, API 응답(JSON 등)을 반환할 때는 `@RestController`를 사용한다.

# 서비스
서비스는 실제 비즈니스 로직을 구현하는 계층이다. 컨트롤러가 요청을 받아 오면, 서비스 계층에서 구체적인 로직을 수행하고 필요한 데이터를 처리한다. 일반적으로 `@Service` 어노테이션을 사용해 정의한다.

1. 비즈니스 로직을 수행하며, 다양한 유효성 검사와 데이터 처리, 트랜잭션을 관리한다.
2. 데이터베이스에서 데이터를 가져오거나 저장할 때는 리포지토리 계층을 호출하여 처리한다.


# 엔티티
엔티티는 데이터베이스의 테이블과 매핑되는 객체이다. 

```
@Builder // Builder 패턴으로 객체 생성
@Entity // 엔티티로 지정
@Getter // getter 메서드 자동 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@NoArgsConstructor // 기본 생성자 자동 추가
public class Article {

    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
```
엔티티 예제

### builder
`@Builder` 어노테이션은 롬복에서 지원하는 어노테이션으로, 빌더 패턴으로 객체를 유연하고 직관적으로 생성할 수 있다.

```
// 빌더 패턴을 사용하지 않았을 때
new Article("abc", "def");

// 빌더 패턴 사용
Article.builder()
	.title("abc")
	.content("def")
	.build();
```
빌더 패턴의 예

# DTO
DTO(Data Transfer Object)는 주로 계층 간 데이터 전송을 위해 사용되는 객체다. DB 엔티티와는 달리, 비즈니스 로직이 포함되지 않고 순수하게 데이터만을 담는 객체다. `Request`와 `Response` 객체는 DTO의 일종이라고 볼 수 있다.

# 트랜잭션
트랜잭션은 데이터베이스에서 데이터를 바꾸기 위한 작업 단위를 말한다. 예를 들어 계좌 이체를 할 때 이런 과정을 거친다고 해본다.
1. A 계좌에서 출금
2. B 계좌에 입금

그런데 A 계좌에서 출금을 성공하고, B 계좌에서 입금을 진행하는 도중 실패하면 고객 입장에서는 출금은 됐는데 입금이 안 된 심각한 상황이 벌어지기 때문에, 
이런 상황이 발생하지 않으려면 출금과 입금을 하나의 작업 단위로 묶어야 한다. 즉, 트랜잭션으로 묶어서 두 작업을 한 단위로 실행하고, 만약 중간에 실패한다면
트랜잭션의 처음 상태로 모두 되돌리면 된다.

트랜잭션은 여러 테이블에 데이터를 삽입하거나 수정해야 하는 경우 쓰인다.

# 타임 리프
타임리프는 템플릿 엔진이다. 템플릿 엔진은 스프링 서버에서 데이터를 받아 우리가 보는 웹 페이지, 즉, HTML 상에 그 데이터를 넣어 보여주는 도구를 말한다.

```
<h1 text=${이름}>
<p text=${나이}>
```
간단한 템플릿 문법, 서버에서 이름, 나이라는 키로 데이터를 템플릿 엔진에 넘겨주고 템플릿 엔진은 이를 받아 HTML에 값을 적용한다.

### 타임 리프 표현식과 문법
표현식
- `${}`: 변수의 값 표현식
- `#{}`: 속성 파일 값 표현식
- `@{}`: URL 표현식
- `*{}`: 선택한 변수의 표현식.th:object에서 선택한 객체에 접근

문법
- `th:text`: 텍스트를 표현할 때 사용 ex) th:text=${person.name}
- `th:each`: 컬렉션을 반복할 때 사용 ex) th:each="person:${persons}"
- `th:if`: 조건이 true인 때만 표시 ex) th:if="${person.age}>=20"
- `th:unless`: 조건이 false인 때만 표시 ex) th:unless="${person.age}>=20"
- `th:href`: 이동 경로 ex) th:href="@{/persons(id=${person.id})}"
- `th:with`: 변숫값으로 지정 ex) th:with="name=${person.name}"
- `th:object`: 선택한 객체로 지정 ex) th:object="${person}"
