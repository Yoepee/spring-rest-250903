package com.back.domain.post.postComment.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.domain.post.postComment.dto.PostCommentDto;
import com.back.domain.post.postComment.entity.PostComment;
import com.back.domain.post.postComment.service.PostCommentService;
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
@RequestMapping("/api/v1/posts/{postId}/comments")
public class ApiV1PostCommentController {
    private final PostService postService;
    private final PostCommentService postCommentService;

    @GetMapping("")
    @Transactional(readOnly = true)
    public List<PostCommentDto> getComments(@PathVariable Long postId){
        Post post = postService.getPost(postId);
        return post.getPostComments().stream()
                .map(PostCommentDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PostCommentDto getComment(@PathVariable Long postId, @PathVariable Long id){
        Post post = postService.getPost(postId);

        return new PostCommentDto(postCommentService.getCommentById(post, id));
    }

    @GetMapping("/{id}/delete")
    @Transactional
    public Map<String, Object> delete(@PathVariable Long postId, @PathVariable Long id){
        Post post = postService.getPost(postId);
        PostComment postComment = postCommentService.getCommentById(post, id);
        postCommentService.delete(post, postComment);

        Map<String, Object> rsData = new LinkedHashMap<>();
        rsData.put("resultCode", "200-1");
        rsData.put("message", "%d번 댓글이 삭제되었습니다.".formatted(id));
        return rsData;
    }
}
