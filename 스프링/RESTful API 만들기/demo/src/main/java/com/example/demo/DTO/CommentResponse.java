package com.example.demo.DTO;

import com.example.demo.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

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
