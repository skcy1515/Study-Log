# 엔티티
엔티티는 데이터베이스의 테이블과 매핑되는 객체이다. 

### 주제: 게시판 시스템 (Post와 Comment는 1:N 관계)
1. Post 엔티티 (게시글)
- id: 게시글 고유 ID (Long)
- title: 게시글 제목 (String)
- content: 게시글 내용 (String)
- author: 작성자 이름 (String)
- createdAt: 작성 일자 (LocalDateTime)

2. Comment 엔티티 (댓글)
- id: 댓글 고유 ID (Long)
- content: 댓글 내용 (String)
- author: 작성자 이름 (String)
- createdAt: 작성 일자 (LocalDateTime)
- post: 댓글이 속한 게시글 (Post)

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
