package com.junlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junlog.domain.Session;
import com.junlog.domain.User;
import com.junlog.request.Login;
import com.junlog.request.PostCreate;
import com.junlog.request.Signup;
import com.junlog.respository.PostRepository;
import com.junlog.respository.SessionRepository;
import com.junlog.respository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
       userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test() throws Exception {

        userRepository.save(User.builder()
                .name("준아")
                .email("juna1234@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("juna1234@naver.com")
                .password("1234")
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(login);
        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력하세요."))
                .andDo(print());


    }

    @Test
    @DisplayName("로그인 성공후 세션 1개 생성")
    @Transactional
    void test2() throws Exception {

        User user = userRepository.save(User.builder()
                .name("준아")
                .email("juna1234@naver.com")
                .password("1234")
                .build());


        Login login = Login.builder()
                .email("juna1234@naver.com")
                .password("1234")
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        User loggedInUser = userRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);


        assertEquals(1, loggedInUser.getSessions().size());

    }



    @Test
    @DisplayName("로그인 성공후 새션 응답")
    void test3() throws Exception {

        User user = userRepository.save(User.builder()
                .name("준아")
                .email("juna1234@naver.com")
                .password("1234")
                .build());


        Login login = Login.builder()
                .email("juna1234@naver.com")
                .password("1234")
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andDo(print());

    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지 접속한다. /foo")
    void test4() throws Exception {

        //given
        User user = User.builder()
                .name("준아")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        Session session = user.addSession();
        userRepository.save(user);

        //when

        //then

        mockMvc.perform(get("/foo")
                        .header("Authorization", session.getAccessToken())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @DisplayName("로그인 후 검증되지 않은 세션값으로 권한이 필요한 페이지에 접속 할 수 없다.")
    void test5() throws Exception {

        //given
        User user = User.builder()
                .name("준아")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        Session session = user.addSession();
        userRepository.save(user);

        //when

        //then

        mockMvc.perform(get("/foo")
                        .header("Authorization", session.getAccessToken() + "-other")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @DisplayName("회원 가입")
    void test6() throws Exception {

        //given

        Signup signup = Signup.builder()
                .name("juna")
                .email("juna1234@naver.com")
                .password("1234")
                .build();



        //when

        //then

        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

}