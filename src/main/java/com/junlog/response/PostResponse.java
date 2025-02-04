package com.junlog.response;

import com.junlog.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getRegDate();
    }

    @Builder
    public PostResponse(Long id, String title, String content, LocalDateTime regDate) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
        this.regDate = regDate;
    }

}
