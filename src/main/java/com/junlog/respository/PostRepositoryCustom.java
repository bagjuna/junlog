package com.junlog.respository;

import com.junlog.domain.Post;
import com.junlog.request.PostSearch;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
