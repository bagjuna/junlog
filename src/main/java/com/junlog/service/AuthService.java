package com.junlog.service;

import com.junlog.domain.Session;
import com.junlog.domain.User;
import com.junlog.exception.InvalidSigninInformation;
import com.junlog.request.Login;
import com.junlog.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigninInformation());

        Session session = user.addSession();

        return session.getAccessToken();

    }


}
