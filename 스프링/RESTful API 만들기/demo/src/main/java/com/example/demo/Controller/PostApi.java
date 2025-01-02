package com.example.demo.Controller;

import com.example.demo.DTO.CommentRequest;
import com.example.demo.DTO.PostRequest;
import com.example.demo.DTO.PostResponse;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
