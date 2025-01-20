package com.junlog.service;

import com.junlog.domain.User;
import com.junlog.exception.AlreadyExistEmailException;
import com.junlog.exception.InvalidSigninInformation;
import com.junlog.request.Login;
import com.junlog.request.Signup;
import com.junlog.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signin(Login login) {

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);


        var matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
        if(!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();

    }


    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository
                .findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistEmailException();
        }

        String encryptedPassword = passwordEncoder.encode(signup.getPassword());

        var user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);

    }
}
