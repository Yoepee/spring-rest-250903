package com.back.domain.post.postComment.service;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.postComment.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    public PostComment create(Post post, String content) {
        return post.addPostComment(content);
    }

    public boolean delete(Post post, PostComment postComment) {
        return post.deleteComment(postComment);
    }

    public void update(Post post, PostComment postComment, String content) {
        post.modifyComment(postComment, content);
    }

    public PostComment getCommentById(Post post, long id) {
        return post.findCommentById(id).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
    }
}
