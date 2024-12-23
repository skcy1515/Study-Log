# 스프링이란?
스프링은 엔터프라이즈용 자바 애플리케이션 개발을 편하게 할 수 있게 해주는 프레임워크이다.

- 엔터프라이즈 애플리케이션: 대규모의 복잡한 데이터를 관리하는 애플리케이션
- 프레임워크: 소프트웨어 개발을 수월하게 하기 위한 소프트웨어 개발 환경
- 라이브러리: 애플리케이션 개발 에 필요한 기능인 클래스, 함수 등을 모아 놓은 코드의 모음

# 스프링 부트란?
스프링 부트는 스프링 프레임 워크를 더 쉽고 빠르게 이용할 수 있도록 만들어주는 도구이다. 스프링 부트는 자동으로 개발 환경 구성을 해주고, WAS(web application server)를 자체적으로 가지고 있다.

# 스프링의 주요 특징
## IoC (제어의 역전) / DI (의존성 주입)
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

## 빈과 스프링 컨테이너
스프링은 스프링 컨테이너를 제공한다. 스프링 컨테이너는 빈을 생성하고 관리한다. 즉, 빈이 생성되고 소멸되기까지의 생명주기를 이 스프링 컨테이너가 관리한다. 또한 개발자가 `@Autowired` 같은 애너테이션을 사용해 빈을 주입받을 수 있게 DI를 지원하기도 한다.

빈은 스프링 컨테이너가 생성하고 관리하는 객체이다. 스프링 부트에서 사용자 클래스를 스프링 빈으로 등록하는 가장 쉬운 방법은 클래스 선언부 위에 `@Component` 어노테이션을 사용하는 것이다. `@Controller`, `@Service`, `@Repository`는 모두 `@Component`를 포함하고 있으며 해당 어노테이션으로 등록된 클래스들은 스프링 컨테이너에 의해 자동으로 생성되어 스프링 빈으로 등록된다.

## AOP (관점 지향 프로그래밍)
AOP(Aspect Oriented Programming)는 프로그래밍을 할 때 핵심 관점과 부가 관점을 나누어서 개발하는 것을 말한다. 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화하겠다는 것이다. 여기서 모듈화란 어떤 공통된 로직이나 기능을 하나의 단위로 묶는 것을 말한다. 
 
예로들어 핵심적인 관점은 결국 우리가 적용하고자 하는 핵심 비즈니스 로직이 된다. 또한 부가적인 관점은 핵심 로직을 실행하기 위해서 행해지는 데이터베이스 연결, 로깅, 파일 입출력 등을 예로 들 수 있다.

## PSA (이식 가능한 서비스 추상화)
PSA(Portable Service Abstraction)은 스프링에서 제공하는 다양한 기술들을 추상화 해 개발자가 쉽게 사용하는 인터페이스를 말한다. 대표적인 PSA의 예로는 클라이언트의 매핑과 클래스, 메서드의 매핑을 위한 애너테이션이 있습니다. 또한 스프링에서 데이터베이스에 접근하기 위한 기술인 JPA가 있다.

## MVC 패턴
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

# 트랜잭션
트랜잭션은 데이터베이스에서 데이터를 바꾸기 위한 작업 단위를 말한다. 예를 들어 계좌 이체를 할 때 이런 과정을 거친다고 해본다.
1. A 계좌에서 출금
2. B 계좌에 입금

그런데 A 계좌에서 출금을 성공하고, B 계좌에서 입금을 진행하는 도중 실패하면 고객 입장에서는 출금은 됐는데 입금이 안 된 심각한 상황이 벌어지기 때문에, 
이런 상황이 발생하지 않으려면 출금과 입금을 하나의 작업 단위로 묶어야 한다. 즉, 트랜잭션으로 묶어서 두 작업을 한 단위로 실행하고, 만약 중간에 실패한다면
트랜잭션의 처음 상태로 모두 되돌리면 된다.

트랜잭션은 여러 테이블에 데이터를 삽입하거나 수정해야 하는 경우 쓰인다.

# DispatcherServlet
DispatcherServlet은 프론트 컨트롤러(Front Controller)로 전체 요청 처리 흐름의 핵심 역할을 한다. 

- SpringApplication.run() → 스프링 컨테이너 초기화 및 Bean 등록
- DispatcherServlet → 클라이언트 요청 수신
- HandlerMapping → 요청 URL과 컨트롤러 매핑
- Controller → 요청 처리 및 비즈니스 로직 실행
- Service → Repository → 데이터베이스 연동 및 데이터 처리
- View Resolver 또는 REST(Json) 응답 → 최종 결과 생성
- 클라이언트로 응답 반환

### 1. 요청 가로채기
- HTTP 요청이 들어오면 DispatcherServlet이 이를 받는다.
- 스프링 부트의 경우, @SpringBootApplication 설정으로 DispatcherServlet이 자동으로 등록된다.

### 2. 핸들러 검색 (HandlerMapping)
- 요청 URL을 분석하여 적합한 컨트롤러와 메서드를 찾는다.
- 이 과정에서 HandlerMapping을 사용한다.
- 예: 요청 URL /users는 UserController의 특정 메서드로 매핑된다.

### 3. 요청 처리 위임 (HandlerAdapter)
- HandlerMapping을 통해 찾은 핸들러(컨트롤러)를 실행하기 위해 HandlerAdapter를 사용한다.
- HandlerAdapter는 컨트롤러 메서드를 호출하고, 요청에 따라 응답 데이터를 반환한다.
