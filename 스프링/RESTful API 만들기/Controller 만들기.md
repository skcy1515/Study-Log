# 컨트롤러
컨트롤러는 클라이언트의 요청을 받아들이고, 해당 요청을 처리할 비즈니스 로직을 서비스에 전달하는 역할을 한다. 컨트롤러는 주로 `@RestController` 어노테이션을 사용해 정의한다.

1. 클라이언트가 보낸 HTTP 요청을 받는다.
2. 요청에 따라 필요한 서비스 메서드를 호출하고, 결과를 클라이언트에게 반환한다.
3. 클라이언트가 웹 페이지를 요청하는 경우 `@Controller`를 사용하고, API 응답(JSON 등)을 반환할 때는 `@RestController`를 사용한다.

# [코드](https://github.com/skcy1515/Study-Log/blob/main/%EC%8A%A4%ED%94%84%EB%A7%81/RESTful%20API%20%EB%A7%8C%EB%93%A4%EA%B8%B0/demo/src/main/java/com/example/demo/Controller/PostApi.java)
```
@RestController
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    // 게시물 생성
    @PostMapping("/post")
    public ResponseEntity<Void> createPost(
            @RequestBody PostRequest postRequest
    ) {
        postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 댓글 생성
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<Void> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        postService.createComment(postId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 게시물 변경
    @PutMapping("/post/{postId}")
    public ResponseEntity<Void> editPost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest
    ) {
        postService.editPost(postId, postRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 댓글 변경
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Void> editComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest
    ) {
        postService.editComment(commentId, commentRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 게시글 삭제 (해당 댓글들도 함께 삭제)
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        postService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 특정 게시물과 그 댓글들 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponse> getPostWithComments(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPostWithComments(postId);
        return ResponseEntity.ok(postResponse);
    }

    // 모든 게시물 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> postResponses = postService.getAllPosts();
        return ResponseEntity.ok(postResponses);
    }
}
```
# 코드 설명
## 1. 어노테이션
```
@RestController
@RequiredArgsConstructor
```
- `@RestController`: 이 클래스가 REST API 요청을 처리하는 컨트롤러임을 나타낸다
- `@RequiredArgsConstructor`: 롬복(Lombok)에서 제공하는 어노테이션으로, final이나 @NonNull로 선언된 모든 필드에 대해 생성자를 자동으로 생성, 의존성 주입 시 사용되며, 주입 방식을 생성자 주입으로 고정

## 2. 필드
```
private final PostService postService;
```
- `postService`: 비즈니스 로직을 처리하는 서비스 계층 객체를 주입받는다

## 3. 게시물 생성
```
@PostMapping("/post")
public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
    postService.createPost(postRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
}
```
- 클라이언트에서 전송된 게시글 데이터를 받아 새로운 게시글을 생성
- `@RequestBody PostRequest postRequest`: 요청 본문(body)의 JSON 데이터를 PostRequest 객체로 매핑
- `postService.createPost(postRequest)`: 서비스 계층의 createPost 메서드를 호출하여 게시글 생성 로직 처리
- `ResponseEntity.status(HttpStatus.CREATED).build()`: HTTP 상태 코드 201(CREATED)을 클라이언트에게 응답

## 4. 댓글 생성
```
@PostMapping("/post/{postId}/comment")
public ResponseEntity<Void> createComment(
        @PathVariable Long postId,
        @RequestBody CommentRequest commentRequest
) {
    postService.createComment(postId, commentRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
}
```
- 클라이언트에서 전송된 게시글 데이터를 받아 새로운 댓글을 생성
- `@PathVariable Long postId`: URL 경로에 포함된 {postId} 값을 추출해 변수로 전달
- `@RequestBody CommentRequest commentRequest`: 요청 본문(body)의 JSON 데이터를 CommentRequest 객체로 매핑
- `postService.createComment(postId, commentRequest)`: 서비스 계층의 createComment 메서드를 호출하여 댓글 생성 로직 처리
- `ResponseEntity.status(HttpStatus.CREATED).build()`: HTTP 상태 코드 201(CREATED)을 클라이언트에게 응답

## 5. 특정 게시물과 댓글 조회
```
@GetMapping("/post/{postId}")
public ResponseEntity<PostResponse> getPostWithComments(@PathVariable Long postId) {
    PostResponse postResponse = postService.getPostWithComments(postId);
    return ResponseEntity.ok(postResponse);
}
```
- `postService.getPostWithComments(postId)`: postId로 게시글을 조회하고, PostResponse 객체로 변환
- `ResponseEntity.ok(postResponse)`: HTTP 상태 코드 200(OK)과 함께 게시글 데이터를 JSON 형식으로 응답

## 6. 모든 게시물 조회
```
@GetMapping("/posts")
public ResponseEntity<List<PostResponse>> getAllPosts() {
    List<PostResponse> postResponses = postService.getAllPosts();
    return ResponseEntity.ok(postResponses);
}
```
- `postService.getAllPosts()`: 데이터베이스에서 모든 게시글을 조회하고, PostResponse 리스트로 변환
- `ResponseEntity.ok(postResponses)`: HTTP 상태 코드 200(OK)과 함께 게시글 리스트 데이터를 JSON 형식으로 응답
