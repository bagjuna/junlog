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

// 데이터 응답 방법
// Case 1 저장한 데이터
// Case 2 저장한 데이터 primary_id로 응답하기
//      Client에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음
// Case 3 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함
// Bad Case: 서버에서 -> 반드시 이렇게 할껍니다! fix
//                  -> 서버에서 차라리 유연하게 대응하는게 좋습니다. -> 코드를 잘 짜야겠죠?!
//                  -> 한 번에 일관적으로 잘 처리되는 케이스가 없습니다. -> 잘 관리하는 형태가 중요합니다.


import com.junlog.domain.Post;
import com.junlog.exception.InvalidRequest;
import com.junlog.request.PostCreate;
import com.junlog.request.PostEdit;
import com.junlog.request.PostSearch;
import com.junlog.response.PostResponse;
import com.junlog.service.PostService;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // 1. GET Parameter
        // 2. POST(body) value
        // 3.
//        request.validate();
        postService.write(request);

    }

    /**
     * 조회 Api
     * 지난 시간 = 단건 조회 API (1개의 글 Post)
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }


    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }


}
