package com.junlog.response;

import com.junlog.domain.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
