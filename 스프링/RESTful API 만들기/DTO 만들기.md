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

## 2. PostRequest

## 3. CommentRequest

## 4. PostResponse

## 5. CommentResponse
