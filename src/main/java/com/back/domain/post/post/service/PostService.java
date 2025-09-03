package com.back.domain.post.post.service;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.repostiory.PostRepository;
import com.back.domain.post.postComment.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public long count() {
        return postRepository.count();
    }

    public List<Post> getList() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
    }

    public Post create(String title, String content) {
        Post post = new Post(title, content);

        return postRepository.save(post);
    }

    public void update(Post post, String title, String content) {
        post.modify(title, content);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public void createComment(Post post, String content) {
        post.addPostComment(content);
    }

    public boolean deleteComment(Post post, PostComment postComment) {
        return post.deleteComment(postComment);
    }

    public void modifyComment(Post post, PostComment postComment, String content) {
        post.modifyComment(postComment, content);
    }

    public PostComment findCommentById(Post post, long id){
        return post.findCommentById(id).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
    }
}
