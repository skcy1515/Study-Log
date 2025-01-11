# 서비스
서비스는 실제 비즈니스 로직을 구현하는 계층이다. 컨트롤러가 요청을 받아 오면, 서비스 계층에서 구체적인 로직을 수행하고 필요한 데이터를 처리한다. 일반적으로 `@Service` 어노테이션을 사용해 정의한다.

1. 비즈니스 로직을 수행하며, 다양한 유효성 검사와 데이터 처리, 트랜잭션을 관리한다.
2. 데이터베이스에서 데이터를 가져오거나 저장할 때는 리포지토리 계층을 호출하여 처리한다.

# [코드](https://github.com/skcy1515/Study-Log/blob/main/%EC%8A%A4%ED%94%84%EB%A7%81/RESTful%20API%20%EB%A7%8C%EB%93%A4%EA%B8%B0/demo/src/main/java/com/example/demo/service/PostService.java)
```
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 게시물 생성
    public void createPost(
            PostRequest postRequest
    ){
        PostEntity postEntity = PostEntity.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        postRepository.save(postEntity);
    }

    // 댓글 생성
    public void createComment(
            Long postId, CommentRequest commentRequest
    ){
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

        CommentEntity commentEntity = CommentEntity.builder()
                .content(commentRequest.getContent())
                .author(commentRequest.getAuthor())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .post(postEntity)
                .build();

        commentRepository.save(commentEntity);
    }

    // 게시물 변경
    public void editPost(
            Long postId, PostRequest postRequest
    ){
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

        postEntity.changePost(postRequest.getTitle(), postRequest.getContent(), postRequest.getAuthor());
        postRepository.save(postEntity);
    }

    // 댓글 변경
    public void editComment(
            Long commentId, CommentRequest commentRequest
    ) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentEntity.changeComment(commentRequest.getAuthor(), commentRequest.getContent());
        commentRepository.save(commentEntity);
    }

    // 게시글 삭제 (해당하는 댓글들도 함께 삭제)
    public void deletePost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow();
        postRepository.delete(postEntity);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow();
        commentRepository.delete(commentEntity);
    }

    // 특정 게시물과 그 댓글들 조회
    public PostResponse getPostWithComments(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));
        return new PostResponse(postEntity);
    }

    // 모든 게시물 조회
    public List<PostResponse> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
```
# 코드 설명
## 1. 어노테이션
```
@Service
@RequiredArgsConstructor
```
- `@Service`: 이 어노테이션은 해당 클래스가 서비스 역할을 한다는 것을 스프링 프레임워크에 알려줌
- `@RequiredArgsConstructor`: 롬복에서 제공하는 어노테이션으로, final이나 @NonNull로 선언된 모든 필드를 매개변수로 갖는 생성자를 자동으로 생성, 생성자 주입 방식으로 의존성을 주입할 때 사용

## 2. 필드
```
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
```
- `postRepository`: 게시글(PostEntity) 데이터를 관리하기 위한 데이터베이스 접근 객체
    - JpaRepository를 확장하여 기본적인 CRUD 기능을 자동으로 제공
    - PostRepository 인터페이스는 데이터베이스 작업을 수행하는 DAO(Data Access Object) 역할을 함
- `commentRepository`: 댓글(CommentEntity) 데이터를 관리하기 위한 데이터베이스 접근 객체

## 3. 게시물 생성 (createPost)
```
public void createPost(PostRequest postRequest) {
    PostEntity postEntity = PostEntity.builder()
            .title(postRequest.getTitle())
            .content(postRequest.getContent())
            .author(postRequest.getAuthor())
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();

    postRepository.save(postEntity);
}
```
- 클라이언트에서 받은 요청 데이터(PostRequest)를 기반으로 PostEntity를 생성
- 생성 시 빌더 패턴을 이용해 필드를 초기화
- 저장소(postRepository)에 저장

### 왜 postEntity를 생성하고 저장하는가?
1. 클라이언트(예: 웹 프론트엔드 또는 API 요청)에서 전달받은 데이터(PostRequest)는 DTO(Data Transfer Object)로 사용된다.
2. 이 데이터를 엔티티(PostEntity)로 변환해야만 JPA를 통해 데이터베이스 작업이 가능하다.
3. postRepository.save(postEntity)를 호출하면 JPA는 PostEntity를 분석해 데이터베이스의 post 테이블에 해당 데이터를 삽입하는 SQL(INSERT)을 생성한다.
4. 생성된 SQL이 실행되어 데이터베이스에 데이터가 저장된다.

* postRepository: 데이터베이스와 연결된 저장소 객체로, JPA를 통해 PostEntity를 저장, 조회, 수정, 삭제하는 역할을 담당

## 4. 댓글 생성 (createComment)
```
public void createComment(Long postId, CommentRequest commentRequest) {
    PostEntity postEntity = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

    CommentEntity commentEntity = CommentEntity.builder()
            .content(commentRequest.getContent())
            .author(commentRequest.getAuthor())
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .post(postEntity) // 조회한 게시글을 댓글의 post 필드에 설정
            .build();

    commentRepository.save(commentEntity);
}
```
- postId로 게시글을 조회. 없으면 예외를 발생시킴
- `post(postEntity)`: 조회한 PostEntity를 CommentEntity의 post 필드에 설정하여 댓글이 어떤 게시글에 속해 있는지 관계를 명시, 외래키(post_id) 생성
- CommentEntity를 빌더 패턴으로 생성 후 저장소에 저장

## 5. 게시물 변경 (editPost)
```
public void editPost(Long postId, PostRequest postRequest) {
    PostEntity postEntity = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

    postEntity.changePost(postRequest.getTitle(), postRequest.getContent(), postRequest.getAuthor());
    postRepository.save(postEntity);
}
```
- postId로 게시글을 조회. 없으면 예외를 발생시킴
- 조회한 엔티티의 changePost 메서드를 호출하여 내용을 수정
- 수정된 엔티티를 저장소에 저장

## 6. 댓글 변경 (editComment)
```
public void editComment(Long commentId, CommentRequest commentRequest) {
    CommentEntity commentEntity = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

    commentEntity.changeComment(commentRequest.getAuthor(), commentRequest.getContent());
    commentRepository.save(commentEntity);
}
```
- commentId로 댓글을 조회. 없으면 예외를 발생시킴
- 조회한 엔티티의 changeComment 메서드를 호출하여 내용을 수정
- 수정된 엔티티를 저장소에 저장

## 7. 게시물 삭제 (deletePost)
```
public void deletePost(Long postId) {
    PostEntity postEntity = postRepository.findById(postId)
            .orElseThrow();

    postRepository.delete(postEntity);
}
```
- postId로 게시글을 조회
- 게시글 엔티티를 삭제

## 8. 댓글 삭제 (deleteComment)
```
public void deleteComment(Long commentId) {
    CommentEntity commentEntity = commentRepository.findById(commentId)
            .orElseThrow();

    commentRepository.delete(commentEntity);
}
```
- commentId로 댓글을 조회
- 조회한 댓글 엔티티를 삭제

## 9. 특정 게시물과 댓글 조회 (getPostWithComments)
```
public PostResponse getPostWithComments(Long postId) {
    PostEntity postEntity = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

    return new PostResponse(postEntity);
}
```
- postId로 게시글을 조회
- 조회한 게시글을 기반으로 PostResponse 객체를 생성하여 반환
- PostResponse 클래스는 게시글과 관련 댓글 정보를 포함하는 DTO 역할을 함

## 10. 모든 게시물 조회 (getAllPosts)
```
public List<PostResponse> getAllPosts() {
    List<PostEntity> postEntities = postRepository.findAll();

    return postEntities.stream()
            .map(PostResponse::new)
            .collect(Collectors.toList());
}
```
- 저장소에서 모든 게시글을 조회
- 조회된 게시글 리스트를 PostResponse로 변환
- 변환된 리스트를 반환

### 스트림 설명
- `postEntities.stream()`: postEntities 리스트를 스트림(Stream)으로 변환
    - 스트림은 데이터 컬렉션(List, Set, 배열 등)을 처리하기 위한 연속 데이터 흐름
- `.map(PostResponse::new)`: PostEntity 객체를 PostResponse 객체로 변환, PostResponse 생성자를 호출
```
    public PostResponse(PostEntity postEntity){
        this.id = postEntity.getId();
        this.title = postEntity.getTitle();
        this.content = postEntity.getContent();
        this.author = postEntity.getAuthor();
        this.createdAt = postEntity.getCreatedAt();
        this.updatedAt = postEntity.getUpdatedAt();
        this.comments = postEntity.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
```
- `.collect(Collectors.toList())`: 변환된 스트림의 결과를 다시 리스트(List)로 수집
