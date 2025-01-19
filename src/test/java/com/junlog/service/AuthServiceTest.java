package com.junlog.service;

import com.junlog.domain.User;
import com.junlog.exception.AlreadyExistEmailException;
import com.junlog.request.Login;
import com.junlog.request.Signup;
import com.junlog.respository.PostRepository;
import com.junlog.respository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class AuthServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() throws Exception{
        //given
        Signup signup = Signup.builder()
                .name("juna")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        //when
        authService.signup(signup);
        //then
        assertEquals(1, userRepository.count());
        User user = userRepository.findAll().iterator().next();
        assertEquals(user.getEmail(), signup.getEmail());
        assertEquals(user.getPassword(), signup.getPassword());
        assertEquals(user.getName(), signup.getName());
    }


    @Test
    @DisplayName("회원 가입시 중복된 이메일")
    void test2() throws Exception{
        //given

        User user = User.builder()
                .email("juna1234@naver.com")
                .password("1234")
                .name("준아")
                .build();

        Signup signup = Signup.builder()
                .name("juna")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        //when
        assertThrows(AlreadyExistEmailException.class, () -> authService.signup(signup));
        //then
    }




}