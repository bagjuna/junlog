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


    @Test
    @DisplayName("회원 가입시 중복된 이메일")
    void test2() throws Exception{
        //given

        User user = User.builder()
                .email("juna1234@naver.com")
                .password("1234")
                .name("준아")
                .build();

        userRepository.save(user);

        Signup signup = Signup.builder()
                .name("juna")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        //when
        assertThrows(AlreadyExistEmailException.class, () -> authService.signup(signup));
        //then
    }


    @Test
    @DisplayName("로그인 성공")
    void test3() throws Exception{
        //given



        Signup signup = Signup.builder()
                .name("juna")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        authService.signup(signup);

        Login login = Login.builder()
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        //when
        authService.signin(login);

        //then
        assertEquals(1, userRepository.count());

    }


    @Test
    @DisplayName("로그인 비밀번호 틀림")
    void test4() throws Exception{
        //given
        Signup signup = Signup.builder()
                .name("juna")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        authService.signup(signup);

        Login login = Login.builder()
                .email("juna1234@naver.com")
                .password("12345")
                .build();

        //when
        assertThrows(InvalidSigninInformation.class, () -> authService.signin(login));

    }


}