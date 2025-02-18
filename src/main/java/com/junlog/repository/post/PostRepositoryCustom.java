package com.junlog.repository.post;

import com.junlog.domain.Post;
import com.junlog.request.post.PostSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostRepositoryCustom {

    Page<Post> getList(PostSearch postSearch);

}
