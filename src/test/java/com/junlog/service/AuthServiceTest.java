package com.junlog.service;

import com.junlog.domain.User;
import com.junlog.exception.AlreadyExistEmailException;
import com.junlog.exception.InvalidSigninInformation;
import com.junlog.request.Login;
import com.junlog.request.Signup;
import com.junlog.respository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
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
//        PasswordEncoder encoder = new PasswordEncoder();
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
//        assertNotEquals("1234", encoder.encrypt(user.getPassword()));
        assertEquals("1234", user.getPassword());
        assertEquals(user.getName(), signup.getName());
    }




}