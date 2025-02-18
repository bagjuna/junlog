package com.junlog.repository.post;

import com.junlog.domain.Post;
import com.junlog.request.post.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.junlog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Post> getList(PostSearch postSearch) {

        Long totalCount = jpaQueryFactory.select(post.count())
                .from(post)
                .fetchFirst();

        List<Post> items = jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();

        return new PageImpl<>(items, postSearch.getPageable(), totalCount);

    }



}
