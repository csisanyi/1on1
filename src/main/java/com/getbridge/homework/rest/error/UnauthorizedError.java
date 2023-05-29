package com.getbridge.homework.rest.error;

public class UnauthorizedError extends IllegalAccessException {

    public UnauthorizedError(String s) {
        super(s);
    }
}
