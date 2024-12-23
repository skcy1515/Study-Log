# 엔티티
엔티티는 데이터베이스의 테이블과 매핑되는 객체이다. 

# 코드

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
