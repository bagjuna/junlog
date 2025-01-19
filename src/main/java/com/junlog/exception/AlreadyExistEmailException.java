package com.junlog.exception;

import com.junlog.controller.JunlogException;

public class AlreadyExistEmailException extends JunlogException {

    private final static String MESSAGE = "이미 가입된 이메일 입니다.";


    public AlreadyExistEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
