# DTO
DTO(Data Transfer Object)는 주로 계층 간 데이터 전송을 위해 사용되는 객체다. DB 엔티티와는 달리, 비즈니스 로직이 포함되지 않고 순수하게 데이터만을 담는 객체다. 

# 코드
```
@Getter
@AllArgsConstructor
public class PostRequest {
    private final String title;
    private final String content;
    private final String author;
    private final List<CommentRequest> comments;
}
```
[PostRequest](https://github.com/skcy1515/Study-Log/blob/main/%EC%8A%A4%ED%94%84%EB%A7%81/RESTful%20API%20%EB%A7%8C%EB%93%A4%EA%B8%B0/demo/src/main/java/com/example/demo/DTO/PostRequest.java)

```
@Getter
@AllArgsConstructor
public class CommentRequest {
    private final String content;
    private final String author;
}
```
[CommentRequest](https://github.com/skcy1515/Study-Log/blob/main/%EC%8A%A4%ED%94%84%EB%A7%81/RESTful%20API%20%EB%A7%8C%EB%93%A4%EA%B8%B0/demo/src/main/java/com/example/demo/DTO/CommentRequest.java)

```
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<CommentResponse> comments;

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
}
```
[PostResponse](https://github.com/skcy1515/Study-Log/blob/main/%EC%8A%A4%ED%94%84%EB%A7%81/RESTful%20API%20%EB%A7%8C%EB%93%A4%EA%B8%B0/demo/src/main/java/com/example/demo/DTO/PostResponse.java)

```
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponse {
    private Long commentID;
    private String content;
    private String author;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public CommentResponse(CommentEntity commentEntity){
        this.commentID = commentEntity.getCommentId();
        this.content = commentEntity.getContent();
        this.author = commentEntity.getAuthor();
        this.createdAt = commentEntity.getCreatedAt();
        this.updatedAt = commentEntity.getUpdatedAt();
    }
}
```
[CommentResponse](https://github.com/skcy1515/Study-Log/blob/main/%EC%8A%A4%ED%94%84%EB%A7%81/RESTful%20API%20%EB%A7%8C%EB%93%A4%EA%B8%B0/demo/src/main/java/com/example/demo/DTO/CommentResponse.java)

# 코드 설명
## 1. 어노테이션
```
@Getter
@AllArgsConstructor
@NoArgsConstructor
```
- `@NoArgsConstructor`: 파라미터 없는 기본 생성자를 자동 생성
- `@AllArgsConstructor`: 모든 필드를 매개변수로 받는 생성자를 자동 생성
- `@Getter`: 모든 필드에 대해 getter 메서드를 자동 생성

## 2. PostRequest
```
@Getter
@AllArgsConstructor
public class PostRequest {
    private final String title;
    private final String content;
    private final String author;
    private final List<CommentRequest> comments;
}
```
- 클라이언트에서 전달받은 게시글 데이터를 표현하는 요청 객체(Request DTO)
- 주로 게시글 생성(Create)이나 수정(Update) 요청 시 사용됨

## 3. CommentRequest
```
@Getter
@AllArgsConstructor
public class CommentRequest {
    private final String content;
    private final String author;
}
```
- 클라이언트에서 전달받은 댓글 데이터를 표현하는 요청 객체(Request DTO)
- 댓글 생성(Create)이나 수정(Update) 요청 시 사용됨

## 4. PostResponse
```
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<CommentResponse> comments;

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
}
```
- 서버에서 클라이언트로 반환하는 게시글 데이터를 표현하는 응답 객체(Response DTO)
- 게시글 데이터와 관련 댓글 데이터(CommentResponse 리스트)를 포함
- `public PostResponse(PostEntity postEntity)`: PostEntity 객체를 받아서 PostResponse로 변환
    - 댓글 리스트(comments)는 PostEntity의 comments 필드를 CommentResponse 객체 리스트로 변환하여 설정

## 5. CommentResponse
```
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponse {
    private Long commentID;
    private String content;
    private String author;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public CommentResponse(CommentEntity commentEntity) {
        this.commentID = commentEntity.getCommentId();
        this.content = commentEntity.getContent();
        this.author = commentEntity.getAuthor();
        this.createdAt = commentEntity.getCreatedAt();
        this.updatedAt = commentEntity.getUpdatedAt();
    }
}
```
- 서버에서 클라이언트로 반환하는 댓글 데이터를 표현하는 응답 객체(Response DTO)
- `public CommentResponse(CommentEntity commentEntity)`: CommentEntity 객체를 받아서 CommentResponse로 변환
