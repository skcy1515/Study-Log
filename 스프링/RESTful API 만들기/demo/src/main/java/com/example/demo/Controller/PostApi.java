package com.example.demo.Controller;

import com.example.demo.DTO.PostRequest;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Void> createPost(
            @RequestBody PostRequest postRequest
    ) {
        postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
