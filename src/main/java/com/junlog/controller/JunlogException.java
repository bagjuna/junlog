package com.junlog.controller;

public abstract class JunlogException extends RuntimeException {
    public JunlogException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
