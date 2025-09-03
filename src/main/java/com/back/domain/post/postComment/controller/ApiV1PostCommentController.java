package com.back.domain.post.postComment.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.domain.post.postComment.dto.PostCommentDto;
import com.back.domain.post.postComment.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class ApiV1PostCommentController {
    private final PostService postService;
    private final PostCommentService postCommentService;
    @GetMapping("")
    public List<PostCommentDto> getComments(@PathVariable Long postId){
        Post post = postService.getPost(postId);
        return post.getPostComments().stream()
                .map(PostCommentDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostCommentDto getComment(@PathVariable Long postId, @PathVariable Long id){
        Post post = postService.getPost(postId);

        return new PostCommentDto(postCommentService.getCommentById(post, id));
    }
}
