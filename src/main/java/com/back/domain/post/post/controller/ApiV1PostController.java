package com.back.domain.post.post.controller;

import com.back.domain.post.post.dto.PostDto;
import com.back.domain.post.post.dto.PostWriteReqBody;
import com.back.domain.post.post.dto.PostWriteResBody;
import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @DeleteMapping("/{id}")
    @Transactional
    public RsData<PostDto> delete(@PathVariable Long id){
        Post post = postService.getPost(id);
        postService.delete(post);

        return new RsData<>(
                "200-1",
                "%d번 게시글이 삭제되었습니다.".formatted(id),
                new PostDto(post)
        );
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<PostWriteResBody> write(@Valid @RequestBody PostWriteReqBody form){
        Post post = postService.create(form.title(), form.content());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new PostWriteResBody(postService.count(), new PostDto(post))
        );
    }
}
