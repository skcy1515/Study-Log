package com.example.demo.Controller;

import com.example.demo.DTO.PostResponse;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostViewController {

    private final PostService postService;

    @GetMapping("/posts-view")
    public String getAllPostsView(Model model) {
        List<PostResponse> postResponses = postService.getAllPosts();
        model.addAttribute("posts", postResponses);

        return "posts-view";
    }
}
