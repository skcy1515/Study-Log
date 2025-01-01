package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostRequest {
    private final String title;
    private final String content;
    private final String author;
    private final List<CommentRequest> comments;
}
