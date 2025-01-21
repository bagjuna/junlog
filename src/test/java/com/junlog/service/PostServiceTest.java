package com.junlog.service;

import com.junlog.domain.Post;
import com.junlog.domain.User;
import com.junlog.exception.PostNotFound;
import com.junlog.repository.UserRepository;
import com.junlog.request.post.PostCreate;
import com.junlog.request.post.PostEdit;
import com.junlog.request.post.PostSearch;
import com.junlog.response.PostResponse;
import com.junlog.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("글 작성")
    void test() {


        User user = User.builder()
                .name("준아")
                .email("juna1234@naver.com")
                .password("1234")
                .build();

        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postService.write(user.getId(), request);

        assertEquals(1L, postRepository.count());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {

        // given

        Post request = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(request);

        // json 응답에서 title값 길이를 최대 10글자로 해주세요.

        // when
        PostResponse post = postService.get(request.getId());

        assertEquals(1L, postRepository.count());

        assertEquals("foo", post.getTitle());
        assertEquals("bar", post.getContent());
    }


    @Test
    @DisplayName("글 여러개 조회")
    void test3() {

        // given
        List<Post> requestPosts = IntStream.range(1, 21)
                .mapToObj(i -> Post.builder()
                        .title("foo " + i)
                        .content("bar " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        assertEquals(10, posts.size());
        assertEquals("foo 20", posts.get(0).getTitle());

    }


    @Test
    @DisplayName("글 1페이지 조회")
    void test4() {

        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제이팍 제목 " + i)
                        .content("반포자이 " + i)
                        .build()).collect(Collectors.toList());


        // given
        postRepository.saveAll(requestPosts);
        Pageable pageable = PageRequest.of(0, 5, DESC, "id");

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();
        // when
        List<PostResponse> posts = postService.getList(postSearch);

        assertEquals(10L, posts.size());
        assertEquals("제이팍 제목 30", posts.get(0).getTitle());
        assertEquals("제이팍 제목 26", posts.get(4).getTitle());
    }


    @Test
    @DisplayName("글 제목 수정")
    void test5() {

        // given
        Post post = Post.builder()
                .title("제이팍")
                .content("반포자이")
                .build();


        postRepository.save(post);
        PostEdit postEdit = PostEdit.builder()
                .title("Jpark")
                .content("반포자이")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("Jpark", changedPost.getTitle());
        assertEquals("반포자이", changedPost.getContent());


    }


    @Test
    @DisplayName("글 내용 수정")
    void test6() {

        // given
        Post post = Post.builder()
                .title("제이팍")
                .content("반포자이")
                .build();


        postRepository.save(post);
        PostEdit postEdit = PostEdit.builder()
                .title("제이팍")
                .content("초가집")
                .build();
        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("제이팍", changedPost.getTitle());
        assertEquals("초가집", changedPost.getContent());


    }

    @Test
    @DisplayName("게시글 삭제")
    void test7() throws Exception {
        //given
        Post post = Post.builder()
                .title("준아")
                .content("반포자이")
                .build();
        postRepository.save(post);

        //when
        postService.delete(post.getId());

        //then
        assertEquals(0, postRepository.count());

    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test8() {

        // given

        Post post = Post.builder()
                .title("제이팍")
                .content("반포자이")
                .build();
        postRepository.save(post);


        // expected
        assertThrows(PostNotFound.class, () -> postService.get(post.getId() + 1L));



    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test9() throws Exception {
        //given
        Post post = Post.builder()
                .title("준아")
                .content("반포자이")
                .build();
        postRepository.save(post);

        //when

        //then
        assertThrows(PostNotFound.class, () -> postService.delete(post.getId() + 1L));

    }


    @Test
    @DisplayName("글 제목 수정 - 존재하지 않는 글")
    void test10() {

        // given
        Post post = Post.builder()
                .title("제이팍")
                .content("반포자이")
                .build();


        postRepository.save(post);
        PostEdit postEdit = PostEdit.builder()
                .title("Jpark")
                .content("반포자이")
                .build();

        // expected
        assertThrows(PostNotFound.class, () ->
                postService.edit(post.getId() + 1L, postEdit)
        );

    }



}