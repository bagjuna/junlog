package com.junlog.service;

import com.junlog.domain.Session;
import com.junlog.domain.User;
import com.junlog.exception.AlreadyExistEmailException;
import com.junlog.exception.InvalidRequest;
import com.junlog.exception.InvalidSigninInformation;
import com.junlog.request.Login;
import com.junlog.request.Signup;
import com.junlog.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigninInformation());

        Session session = user.addSession();

        return user.getId();

    }


    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository
                .findByEmail(signup.getEmail());
        if(userOptional.isPresent()) {
            throw new AlreadyExistEmailException();
        }



        var user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(signup.getPassword()).build();

        userRepository.save(user);

    }
}
