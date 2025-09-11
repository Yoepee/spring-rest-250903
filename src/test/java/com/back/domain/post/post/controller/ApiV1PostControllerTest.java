package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApiV1PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 쓰기")
    void t1() throws Exception {
        ResultActions resultActions = mvc.perform(
                post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "제목 new",
                                  "content": "내용 new"
                                }
                                """)
        ).andDo(print());

        Post post = postService.findLastest().get();

        resultActions
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("write"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.message").value("%d번 게시글이 생성되었습니다.".formatted(post.getId())))
                .andExpect(jsonPath("$.data.id").value(post.getId()))
                .andExpect(jsonPath("$.data.createdDate").value(Matchers.startsWith(post.getCreatedDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.modifiedDate").value(Matchers.startsWith(post.getModifiedDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
                .andExpect(jsonPath("$.data.content").value(post.getContent()));

    }

    @Test
    @DisplayName("글 수정")
    void t2() throws Exception {
        long id = 1;
        ResultActions resultActions = mvc.perform(
                put("/api/v1/posts/%d".formatted(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "제목 update",
                                  "content": "내용 update"
                                }
                                """)
        ).andDo(print());

        Post post = postService.getPostById(id);

        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("update"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.message").value("%d번 게시글이 수정되었습니다.".formatted(post.getId())))
                .andExpect(jsonPath("$.data.post.id").value(post.getId()))
                .andExpect(jsonPath("$.data.post.title").value("제목 update"))
                .andExpect(jsonPath("$.data.post.content").value("내용 update"));
    }

    @Test
    @DisplayName("글 삭제")
    void t3() throws Exception {
        long id = 3;

        ResultActions resultActions = mvc.perform(
                delete("/api/v1/posts/%d".formatted(id))
        ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.message").value("%d번 게시글이 삭제되었습니다.".formatted(id)));
    }

    @Test
    @DisplayName("단건 조회")
    void t4() throws Exception {
        long id = 1;

        ResultActions resultActions = mvc.perform(
                get("/api/v1/posts/%d".formatted(id))
        ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("getItem"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createdDate").isString())
                .andExpect(jsonPath("$.modifiedDate").isString())
                .andExpect(jsonPath("$.title").isString())
                .andExpect(jsonPath("$.content").isString());
    }
}
