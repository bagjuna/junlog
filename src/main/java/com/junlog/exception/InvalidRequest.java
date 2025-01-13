package com.junlog.exception;

import com.junlog.controller.JunlogException;

public class InvalidRequest extends JunlogException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
