package com.peak.training.common.annotation.token;

public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String errorCode) {
        super(errorCode);
    }
}
