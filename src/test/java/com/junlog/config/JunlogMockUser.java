package com.junlog.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = JunlogMockSecurityContext.class)

public @interface JunlogMockUser {


    String name() default "juna";

    String email() default "juna1234@naver.com";

    String password() default "1234";


    String role() default "ROLE_ADMIN";

}
