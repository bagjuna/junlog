package com.junlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junlog.config.JunlogMockUser;
import com.junlog.domain.Comment;
import com.junlog.domain.Post;
import com.junlog.domain.User;
import com.junlog.repository.UserRepository;
import com.junlog.repository.comment.CommentRepository;
import com.junlog.repository.post.PostRepository;
import com.junlog.request.comment.CommentCreate;
import com.junlog.request.comment.CommentDelete;
import com.junlog.request.post.PostCreate;
import com.junlog.request.post.PostEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();
    }


    // 댓글 작성
    @Test
    @JunlogMockUser
    @DisplayName("댓글 작성")
    void test() throws Exception {

        //given
        User user = userRepository.findAll().get(0);

        Post postcreate = Post.builder()
                .title("제이팍")
                .content("반포자이")
                .user(user)
                .build();
        Post post = postRepository.save(postcreate);

        // when
        CommentCreate reqeust = CommentCreate
                .builder()
                .author("익명1")
                .password("123456")
                .content("댓글입니다.!!!!!!!!!!!!!!!")
                .build();
        String json = objectMapper.writeValueAsString(reqeust);

        // then
        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(1L, commentRepository.count());

        Comment savedComment = commentRepository.findAll().get(0);
        assertEquals("익명1", savedComment.getAuthor());
        assertNotEquals("123456", savedComment.getPassword());
        assertTrue(passwordEncoder.matches("123456", savedComment.getPassword()));
        assertEquals("댓글입니다.!!!!!!!!!!!!!!!", savedComment.getContent());

    }

    @Test
    @JunlogMockUser
    @DisplayName("댓글 삭제")
    void test2() throws Exception {

        //given
        User user = userRepository.findAll().get(0);

        Post postcreate = Post.builder()
                .title("제이팍")
                .content("반포자이")
                .user(user)
                .build();
        Post post = postRepository.save(postcreate);
        String encryptedPassword = passwordEncoder.encode("123456");

        Comment comment = Comment.builder()
                .author("홍길동")
                .password(encryptedPassword)
                .content("으 하하하하하하하하하하하하하하하")
                .build();
        comment.setPost(post);


        commentRepository.save(comment);

        CommentDelete request = new CommentDelete("123456");
        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/comments/{commentId}/delete", comment.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(0, commentRepository.count());
    }
}