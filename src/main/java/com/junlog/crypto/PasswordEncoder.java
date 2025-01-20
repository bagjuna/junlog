package com.junlog.crypto;

public interface PasswordEncoder {

    String encrypt(String password);

    boolean matches(String password, String encryptedPassword);
}
