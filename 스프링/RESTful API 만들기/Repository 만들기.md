# 리포지토리
리포지토리는 데이터베이스와 직접 상호작용하는 계층으로, `@Repository` 어노테이션을 사용하여 정의한다. 보통 Spring Data JPA 같은 라이브러리를 사용하여 리포지토리를 쉽게 관리한다.

1. 데이터베이스 CRUD(Create, Read, Update, Delete) 작업을 수행한다.
2. JPA를 이용하면 기본적인 CRUD 기능을 자동으로 제공받을 수 있으며, 필요한 경우 커스텀 쿼리를 작성할 수도 있다.

# JPA
JPA는 자바에서 객체-관계 매핑(ORM)을 제공하는 API이다. 즉, 자바 객체와 데이터베이스 테이블 간의 매핑을 통해, 데이터베이스에 대한 CRUD(Create, Read, Update, Delete) 작업을 간편하게 수행할 수 있게 도와준다.

* ORM: 자바의 객체와 데이터베이스를 연결하는 프로그래밍 기법, SQL을 직접 작성하지 않고 사용하는 언어로 데이터베이스에 접근할 수 있다.

### 스프링 데이터 JPA
스프링 데이터 JPA는 JPA를 쉽게 사용할 수 있도록 도와주는 라이브러리이다. 스프링 데이터의 인터페이스인 PagingAndSortingRepository를 상속받아 JpaRepository 인터페이스를 만들었으며, JPA를 더 편리하게 사용하는 메서드를 제공한다. 

다음과 같이 JpaRepository 인터페이스를 우리가 만든 인터페이스에서 상속받고, 제네릭에는 관리할 <엔티티 이름, 엔티티 기본키의 타입>을 입력하면 기본 CRUD 메서드를 사용할 수 있다.

```
public interface MemberRepository extends JpaRepository<Member, Long>{
}
```

# 코드
