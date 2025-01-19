package com.junlog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;


    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);

    }




    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();


    }



}
