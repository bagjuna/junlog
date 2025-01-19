package com.junlog.controller;

import com.junlog.config.AppConfig;
import com.junlog.request.Login;
import com.junlog.request.Signup;
import com.junlog.response.SessionResponse;
import com.junlog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        log.info(">>>login: {}", login);
        Long userId = authService.signin(login);

//        SecretKey key = getSecretKey();
//        Base64.getEncoder().encode(key.getEncoded());
        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .subject(userId.toString())
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws);
    }

    private static SecretKey getSecretKey() {
        SecretKey key = Jwts.SIG.HS256.key().build(); //or HS384.key() or HS512.key()
        return key;
    }


    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);

    }
}
