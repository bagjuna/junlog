package com.junlog.config;

import com.junlog.config.data.UserSession;
import com.junlog.domain.Session;
import com.junlog.exception.Unauthorized;
import com.junlog.respository.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest == null) {
            log.error("servletRequest null");
            throw new Unauthorized();
        }
        Cookie[] cookies = servletRequest.getCookies();

        if (cookies.length == 0) {
            log.error("cookies null");
            throw new Unauthorized();
        }

        String accessToken = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("SESSION")) {
                accessToken = cookie.getValue();
            }
        }

        if (accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        // 데이터베이스 사용자 확인 작업
        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new Unauthorized());

        // ...

        return new UserSession(session.getUser().getId());

    }

}
