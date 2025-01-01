package com.example.demo.service;

import com.example.demo.DTO.CommentRequest;
import com.example.demo.DTO.PostRequest;
import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.PostEntity;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/*
게시물 생성
댓글 생성
게시물 변경
댓글 변경
게시물 삭제 (댓글들도 삭제)
댓글 삭제
게시물 조회
모든 게시물 조회
*/


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    public void editPost(
            Long postId, PostRequest postRequest
    ){
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

        postEntity.changePost(postRequest.getTitle(), postRequest.getContent(), postRequest.getAuthor());
        postRepository.save(postEntity);
    }
}
