package com.junlog.service;

import com.junlog.domain.User;
import com.junlog.exception.UserNotFound;
import com.junlog.repository.UserRepository;
import com.junlog.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        return new UserResponse(user);

    }

}
