package com.back.domain.post.post.controller;

import com.back.domain.post.post.dto.PostDto;
import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping("")
    @Transactional(readOnly = true)
    public List<PostDto> getItems(){
        return postService.getList().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PostDto getItem(@PathVariable Long id){
        return new PostDto(postService.getPost(id));
    }

    @GetMapping("/{id}/delete")
    @Transactional
    public Map<String, Object> delete(@PathVariable Long id){
        Post post = postService.getPost(id);
        postService.delete(post);
        
        Map<String, Object> rsData = new LinkedHashMap<>();
        rsData.put("resultCode", "200-1");
        rsData.put("message", "%d번 게시글이 삭제되었습니다.".formatted(id));
        return rsData;
    }
}
