package com.nniett.kikaishin.app.web.controller.exception;

public class UsernameNotMatchedException extends RuntimeException {
    public UsernameNotMatchedException(String message) {
        super(message);
    }
}
