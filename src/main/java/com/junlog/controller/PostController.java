package com.junlog.controller;


// SSR -> jsp, thymeleaf, mustache, freemarker
// SPA ->
// vue -> vue+SSR nuxt
// react, next

// Http Method
// GET, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, CONNECT
// 글 등록
// POST Method


// 데이터를 검증하는 이유

// 1. client 개발자가 깜빡할 수 있다. 실수로 값을 안 보낼수 있다.
// 2. client bug로 값이 누락 될 수 있다.
// 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼 수 있다.
// 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
// 5. 서버 개발자의 편안함을 위해서

// 검증
// 1. 빡세다 (노가다)
// 2. 개발 팁 -> 무언가 3번 이상 반복 작업을 할때 내가 뭔가 잘못 하고 있는건 아닐지 의심한다.
// 3. 누락가능성
// 4. 생각보다 검증해야 할게 많다. (꼼꼼하지 않을 수 있다.)
// 5. 뭔가 개발자 스럽지 않다. -> 간지 X


// 1. 매번 메서드마다 값을 검증해야한다.
//     > 개발자가 까먹을 수 있다.
//     > 검증 부분에서 버그가 발생할 여지가 높다.
// 2. 응답값에 HashMap -> 응답 클래스를 만들어주는게 좋습니다.
// 3. 여러 개의 예외처리 힘듦
// 4. 세 번이상의 반복적인 작업은 피해야한다.


import com.junlog.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {


    @GetMapping("/posts")
    public String get() {
        return "Hello World";
    }

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {

        log.info("params={}", params);
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String invalidField = firstFieldError.getField();
            String errorMessage = firstFieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(invalidField, errorMessage);
            return error;

        }
        return Map.of();
    }




}
