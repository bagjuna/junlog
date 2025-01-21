package com.junlog.repository.post;

import com.junlog.domain.Post;
import com.junlog.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
