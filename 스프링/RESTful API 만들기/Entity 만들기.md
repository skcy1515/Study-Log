# 엔티티
엔티티는 데이터베이스의 테이블과 매핑되는 객체이다. 

## 주제: 게시판 시스템 (Post와 Comment는 1:N 관계)
### 1. Post 엔티티 (게시글)
- id: 게시글 고유 ID (Long)
- title: 게시글 제목 (String)
- content: 게시글 내용 (String)
- author: 작성자 이름 (String)
- createdAt: 작성 일자 (LocalDateTime)

### 2. Comment 엔티티 (댓글)
- id: 댓글 고유 ID (Long)
- content: 댓글 내용 (String)
- author: 작성자 이름 (String)
- createdAt: 작성 일자 (LocalDateTime)
- post: 댓글이 속한 게시글 (Post)

# 코드
```
@Entity
@Table(name="post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();

    public void changePost (String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
        this.updatedAt = ZonedDateTime.now();
    }
}
```
PostEntity

```
@Entity
@Table(name="comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    public void changeComment (String content, String author){
        this.content = content;
        this.author = author;
        this.updatedAt = ZonedDateTime.now();
    }
}
```
CommentEntity

# 코드 설명
## 1. 어노테이션
```
@Entity
@Table(name="post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
```
- `@Entity`: 이 클래스가 JPA 엔티티임을 나타낸다. 데이터베이스 테이블과 매핑됨
- `@Table(name="post")`: 엔티티 이름 지정
- `@Getter`: 클래스의 모든 필드에 대한 Getter 메서드를 자동 생성
- `@NoArgsConstructor`: 기본 생성자(파라미터 없는 생성자) 자동 생성
- `@AllArgsConstructor`: 모든 필드를 포함하는 생성자 자동 생성
- `@Builder`: 빌더 패턴을 적용해 객체 생성 시 가독성과 유연성을 높임

### builder
`@Builder` 어노테이션은 롬복에서 지원하는 어노테이션으로, 객체 생성 과정에서 복잡한 초기화 로직을 캡슐화하고, 가독성 좋고 유연한 방식으로 객체를 생성하기 위해 사용되는 디자인 패턴이다.

```
// 빌더 패턴을 사용하지 않았을 때
new Article("abc", "def");

// 빌더 패턴 사용
Article.builder()
	.title("abc")
	.content("def")
	.build();
```

## 2. 필드
```
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String title;

@Column(nullable = false, updatable = false)
private ZonedDateTime createdAt;
```
- `@Id`: 필드가 기본 키임을 지정
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: 데이터베이스가 기본 키를 자동 생성하도록 설정
- `@Column(nullable = false)`: 해당 필드 값이 null일 수 없음을 지정
- `updatable = false`: 값이 변경되지 않음

## 3. 관계
```
@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
@Builder.Default
private List<CommentEntity> comments = new ArrayList<>();

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "post_id", nullable = false)
private PostEntity post;
```
- `@OneToMany`: PostEntity가 여러 개의 CommentEntity와 연결됨(1:N 관계)
- `mappedBy = "post"`: CommentEntity 안에 있는 private PostEntity post 필드가 연관 관계의 주인이라는 것을 뜻함
- `cascade = CascadeType.ALL`: 게시글의 저장/삭제가 댓글에도 전파됨
- `orphanRemoval = true`: 부모가 삭제되면 자식도 데이터베이스에서 삭제됨
- `@Builder.Default`: 빌더를 통해 객체를 생성할 때 comments가 null이 아닌 빈 리스트로 안전하게 초기화됨
- `@ManyToOne`: 댓글(CommentEntity)이 게시글(PostEntity)과 다대일(N:1) 관계임을 나타냄
- `fetch = FetchType.LAZY`: 연관된 게시글 정보는 필요할 때만 로드된다(성능 최적화)
- `@JoinColumn(name = "post_id", nullable = false)`: 댓글 테이블에서 post_id 열을 외래 키로 지정하며, 반드시 값이 있어야 함

### mappedBy 사용 이유
만약 양방향 매핑을 적용한다면 Comment 객체뿐만 아니라 Post 객체도 comments 필드를 통해 COMMENT 테이블에 접근이 가능해지기 때문에 혼란이 생길 수 있다. Comment 객체와 Post 객체가 서로 COMMENT 테이블의 데이터 값을 변경하려 한다면 무결성을 해칠 가능성도 생길 것이다.

그렇기 때문에 두 객체중 하나의 객체만 테이블을 관리할 수 있도록 정하는 것이 mappedBy 옵션인 것이다. `mappedBy = "post"`는 부모(Post)가 자식(Comment)의 상태를 읽을 수는 있지만, 직접 조작하지 않는다는 뜻이다.

## 4. 수정 메서드
```
public void changePost (String title, String content, String author){
    this.title = title;
    this.content = content;
    this.author = author;
    this.updatedAt = ZonedDateTime.now();
}
```
서비스 레이어에서 이미 만들어진 엔티티의 필드를 수정할 때 사용
