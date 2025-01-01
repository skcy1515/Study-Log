package com.example.demo.DTO;

import com.example.demo.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        this.createdAt = postEntity.getCreatedAt();
        this.updatedAt = postEntity.getUpdatedAt();
        this.comments = postEntity.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}
