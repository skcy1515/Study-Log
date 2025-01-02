package com.example.demo.service;

import com.example.demo.DTO.CommentRequest;
import com.example.demo.DTO.PostRequest;
import com.example.demo.DTO.PostResponse;
import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.PostEntity;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
