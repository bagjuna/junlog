package com.junlog.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;


    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    public void validate() {
//        if (title.contains("바보")) {
//            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
//        }
//    }

    // 빌더의 장점
    // - 가독성에 좋다. (값 새성에 대한 유연함)
    // - 필요한 값만 받을 수 있다.


}
