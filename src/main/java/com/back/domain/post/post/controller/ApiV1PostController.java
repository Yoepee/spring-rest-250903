package com.back.domain.post.post.controller;

import com.back.domain.post.post.dto.PostDto;
import com.back.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping("")
    public List<PostDto> getItems(){
        return postService.getList().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDto getItem(@PathVariable Long id){
        return new PostDto(postService.getPost(id));
    }
}
