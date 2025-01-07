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
